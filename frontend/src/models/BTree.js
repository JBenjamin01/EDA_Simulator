// Modelo de nodo para B-Tree
class BTreeNode {
    constructor(degree, isLeaf = false) {
        this.degree = degree;
        this.keys = [];
        this.children = [];
        this.isLeaf = isLeaf;
    }

    toJSON() {
        return {
        degree: this.degree,
        keys: this.keys,
        children: this.children.map(child => child ? child.toJSON() : null),
        isLeaf: this.isLeaf
        };
    }
    }

export class BTree {
    constructor(degree = 3) {
        this.root = null;
        this.degree = degree;
    }

    async performOperation(operation, value) {
        switch (operation) {
        case 'insert':
            return this.insert(value);
        case 'delete':
            return this.delete(value);
        case 'search':
            return this.search(value);
        case 'display':
            return this.display();
        default:
            throw new Error(`Operación no soportada: ${operation}`);
        }
    }

    insert(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para el Árbol B.'
        };
        }

        if (!this.root) {
        this.root = new BTreeNode(this.degree, true);
        this.root.keys.push(value);
        return {
            success: true,
            message: `Elemento "${value}" insertado como primer elemento del Árbol B.`
        };
        }

        if (this.root.keys.length === (2 * this.degree) - 1) {
        const newRoot = new BTreeNode(this.degree, false);
        newRoot.children.push(this.root);
        this.splitChild(newRoot, 0);
        this.root = newRoot;
        }

        this.insertNonFull(this.root, value);

        return {
        success: true,
        message: `Elemento "${value}" insertado en el Árbol B. Se mantiene la propiedad de orden y balance.`
        };
    }

    insertNonFull(node, value) {
        let i = node.keys.length - 1;

        if (node.isLeaf) {
        node.keys.push(0);
        while (i >= 0 && node.keys[i] > value) {
            node.keys[i + 1] = node.keys[i];
            i--;
        }
        node.keys[i + 1] = value;
        } else {
        while (i >= 0 && node.keys[i] > value) {
            i--;
        }
        i++;

        if (node.children[i].keys.length === (2 * this.degree) - 1) {
            this.splitChild(node, i);
            if (node.keys[i] < value) {
            i++;
            }
        }
        this.insertNonFull(node.children[i], value);
        }
    }

    splitChild(parent, index) {
        const fullChild = parent.children[index];
        const newChild = new BTreeNode(this.degree, fullChild.isLeaf);

        const mid = this.degree - 1;

        // Mover la mitad de las claves al nuevo nodo
        newChild.keys = fullChild.keys.splice(mid + 1);
        
        // Si no es hoja, mover también los hijos
        if (!fullChild.isLeaf) {
        newChild.children = fullChild.children.splice(mid + 1);
        }

        // Insertar el nuevo hijo en el padre
        parent.children.splice(index + 1, 0, newChild);
        
        // Mover la clave media al padre
        parent.keys.splice(index, 0, fullChild.keys.splice(mid, 1)[0]);
    }

    search(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para buscar.'
        };
        }

        const found = this.searchNode(this.root, value);
        
        return {
        success: found,
        message: found 
            ? `Elemento "${value}" encontrado en el Árbol B.`
            : `Elemento "${value}" no encontrado en el Árbol B.`
        };
    }

    searchNode(node, value) {
        if (!node) return false;

        let i = 0;
        while (i < node.keys.length && value > node.keys[i]) {
        i++;
        }

        if (i < node.keys.length && value === node.keys[i]) {
        return true;
        }

        if (node.isLeaf) {
        return false;
        }

        return this.searchNode(node.children[i], value);
    }

    delete(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para eliminar.'
        };
        }

        if (!this.root) {
        return {
            success: false,
            message: 'El Árbol B está vacío.'
        };
        }

        if (!this.searchNode(this.root, value)) {
        return {
            success: false,
            message: `Elemento "${value}" no encontrado en el Árbol B.`
        };
        }

        this.deleteFromNode(this.root, value);

        // Si la raíz queda vacía, hacer que su único hijo sea la nueva raíz
        if (this.root.keys.length === 0) {
        if (!this.root.isLeaf) {
            this.root = this.root.children[0];
        } else {
            this.root = null;
        }
        }

        return {
        success: true,
        message: `Elemento "${value}" eliminado del Árbol B. Se mantienen las propiedades del árbol.`
        };
    }

    deleteFromNode(node, value) {
        let i = 0;
        while (i < node.keys.length && value > node.keys[i]) {
        i++;
        }

        if (i < node.keys.length && value === node.keys[i]) {
        // Clave encontrada en este nodo
        if (node.isLeaf) {
            node.keys.splice(i, 1);
        } else {
            // Nodo interno - casos más complejos
            if (node.children[i].keys.length >= this.degree) {
            const predecessor = this.getPredecessor(node, i);
            node.keys[i] = predecessor;
            this.deleteFromNode(node.children[i], predecessor);
            } else if (node.children[i + 1].keys.length >= this.degree) {
            const successor = this.getSuccessor(node, i);
            node.keys[i] = successor;
            this.deleteFromNode(node.children[i + 1], successor);
            } else {
            this.merge(node, i);
            this.deleteFromNode(node.children[i], value);
            }
        }
        } else {
        // Clave no está en este nodo
        if (node.isLeaf) {
            return; // Clave no encontrada
        }

        const isLastChild = (i === node.keys.length);

        if (node.children[i].keys.length < this.degree) {
            this.fill(node, i);
        }

        if (isLastChild && i > node.keys.length) {
            this.deleteFromNode(node.children[i - 1], value);
        } else {
            this.deleteFromNode(node.children[i], value);
        }
        }
    }

    getPredecessor(node, index) {
        let current = node.children[index];
        while (!current.isLeaf) {
        current = current.children[current.children.length - 1];
        }
        return current.keys[current.keys.length - 1];
    }

    getSuccessor(node, index) {
        let current = node.children[index + 1];
        while (!current.isLeaf) {
        current = current.children[0];
        }
        return current.keys[0];
    }

    fill(node, index) {
        if (index !== 0 && node.children[index - 1].keys.length >= this.degree) {
        this.borrowFromPrev(node, index);
        } else if (index !== node.keys.length && node.children[index + 1].keys.length >= this.degree) {
        this.borrowFromNext(node, index);
        } else {
        if (index !== node.keys.length) {
            this.merge(node, index);
        } else {
            this.merge(node, index - 1);
        }
        }
    }

    borrowFromPrev(node, index) {
        const child = node.children[index];
        const sibling = node.children[index - 1];

        child.keys.unshift(node.keys[index - 1]);
        node.keys[index - 1] = sibling.keys.pop();

        if (!child.isLeaf) {
        child.children.unshift(sibling.children.pop());
        }
    }

    borrowFromNext(node, index) {
        const child = node.children[index];
        const sibling = node.children[index + 1];

        child.keys.push(node.keys[index]);
        node.keys[index] = sibling.keys.shift();

        if (!child.isLeaf) {
        child.children.push(sibling.children.shift());
        }
    }

    merge(node, index) {
        const child = node.children[index];
        const sibling = node.children[index + 1];

        child.keys.push(node.keys[index]);
        child.keys.push(...sibling.keys);

        if (!child.isLeaf) {
        child.children.push(...sibling.children);
        }

        node.keys.splice(index, 1);
        node.children.splice(index + 1, 1);
    }

    display() {
        if (!this.root) {
        return {
            success: true,
            message: 'El Árbol B está vacío.'
        };
        }

        const levels = [];
        this.displayHelper(this.root, 0, levels);
        
        const displayText = levels.map((level, index) => 
        `Nivel ${index}: [${level.join('] [')}]`
        ).join('\n');

        return {
        success: true,
        message: `Estructura del Árbol B (grado ${this.degree}):\n${displayText}`
        };
    }

    displayHelper(node, level, levels) {
        if (!levels[level]) {
        levels[level] = [];
        }

        levels[level].push(node.keys.join(', '));

        if (!node.isLeaf) {
        for (const child of node.children) {
            this.displayHelper(child, level + 1, levels);
        }
        }
    }

    toJSON() {
        return {
        root: this.root ? this.root.toJSON() : null,
        degree: this.degree
        };
    }

    toArray() {
        const result = [];
        this.collectKeys(this.root, result);
        return result.sort((a, b) => a - b);
    }

    collectKeys(node, result) {
        if (!node) return;
        
        result.push(...node.keys);
        
        if (!node.isLeaf) {
        for (const child of node.children) {
            this.collectKeys(child, result);
        }
        }
    }
}