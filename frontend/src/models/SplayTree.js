// Modelo de nodo para Splay Tree
class SplayNode {
    constructor(data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    toJSON() {
        return {
        data: this.data,
        left: this.left ? this.left.toJSON() : null,
        right: this.right ? this.right.toJSON() : null
        };
    }
    }

export class SplayTree {
    constructor() {
        this.root = null;
    }

    async performOperation(operation, value) {
        switch (operation) {
        case 'insert':
            return this.insert(value);
        case 'delete':
            return this.delete(value);
        case 'search':
            return this.search(value);
        case 'inorder':
            return this.inorderTraversal();
        default:
            throw new Error(`Operación no soportada: ${operation}`);
        }
    }

    rotateRight(node) {
        const newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot;
    }

    rotateLeft(node) {
        const newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot;
    }

    splay(node, value) {
        if (!node || node.data === value) {
        return node;
        }

        // Valor está en el subárbol izquierdo
        if (value < node.data) {
        if (!node.left) return node;

        // Zig-Zig (izquierda-izquierda)
        if (value < node.left.data) {
            node.left.left = this.splay(node.left.left, value);
            node = this.rotateRight(node);
        }
        // Zig-Zag (izquierda-derecha)
        else if (value > node.left.data) {
            node.left.right = this.splay(node.left.right, value);
            if (node.left.right) {
            node.left = this.rotateLeft(node.left);
            }
        }

        return node.left ? this.rotateRight(node) : node;
        }
        // Valor está en el subárbol derecho
        else {
        if (!node.right) return node;

        // Zag-Zag (derecha-derecha)
        if (value > node.right.data) {
            node.right.right = this.splay(node.right.right, value);
            node = this.rotateLeft(node);
        }
        // Zag-Zig (derecha-izquierda)
        else if (value < node.right.data) {
            node.right.left = this.splay(node.right.left, value);
            if (node.right.left) {
            node.right = this.rotateRight(node.right);
            }
        }

        return node.right ? this.rotateLeft(node) : node;
        }
    }

    insert(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para el Splay Tree.'
        };
        }

        if (!this.root) {
        this.root = new SplayNode(value);
        return {
            success: true,
            message: `Elemento "${value}" insertado como raíz del Splay Tree.`
        };
        }

        this.root = this.splay(this.root, value);

        if (this.root.data === value) {
        return {
            success: false,
            message: `Elemento "${value}" ya existe en el Splay Tree.`
        };
        }

        const newNode = new SplayNode(value);

        if (value < this.root.data) {
        newNode.right = this.root;
        newNode.left = this.root.left;
        this.root.left = null;
        } else {
        newNode.left = this.root;
        newNode.right = this.root.right;
        this.root.right = null;
        }

        this.root = newNode;

        return {
        success: true,
        message: `Elemento "${value}" insertado en el Splay Tree y movido a la raíz mediante operación splay.`
        };
    }

    search(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para buscar.'
        };
        }

        if (!this.root) {
        return {
            success: false,
            message: 'El Splay Tree está vacío.'
        };
        }

        this.root = this.splay(this.root, value);

        const found = this.root.data === value;
        
        return {
        success: found,
        message: found 
            ? `Elemento "${value}" encontrado y movido a la raíz del Splay Tree.`
            : `Elemento "${value}" no encontrado en el Splay Tree. El último nodo accedido está ahora en la raíz.`
        };
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
            message: 'El Splay Tree está vacío.'
        };
        }

        this.root = this.splay(this.root, value);

        if (this.root.data !== value) {
        return {
            success: false,
            message: `Elemento "${value}" no encontrado en el Splay Tree.`
        };
        }

        if (!this.root.left) {
        this.root = this.root.right;
        } else {
        const rightSubtree = this.root.right;
        this.root = this.root.left;
        this.root = this.splay(this.root, value);
        this.root.right = rightSubtree;
        }

        return {
        success: true,
        message: `Elemento "${value}" eliminado del Splay Tree. La estructura se ha reorganizado.`
        };
    }

    inorderTraversal() {
        if (!this.root) {
        return {
            success: true,
            message: 'El Splay Tree está vacío.'
        };
        }

        const result = [];
        this.inorderHelper(this.root, result);
        
        return {
        success: true,
        message: `Recorrido In-Order del Splay Tree: [${result.join(', ')}]. Raíz actual: ${this.root.data}.`
        };
    }

    inorderHelper(node, result) {
        if (node) {
        this.inorderHelper(node.left, result);
        result.push(node.data);
        this.inorderHelper(node.right, result);
        }
    }

    toJSON() {
        return {
        root: this.root ? this.root.toJSON() : null
        };
    }

    toArray() {
        const result = [];
        this.inorderHelper(this.root, result);
        return result;
    }
}