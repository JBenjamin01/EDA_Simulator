// Modelo de nodo para AVL Tree
class AVLNode {
    constructor(data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    toJSON() {
        return {
        data: this.data,
        height: this.height,
        left: this.left ? this.left.toJSON() : null,
        right: this.right ? this.right.toJSON() : null
        };
    }
    }

export class AVLTree {
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

    getHeight(node) {
        return node ? node.height : 0;
    }

    getBalance(node) {
        return node ? this.getHeight(node.left) - this.getHeight(node.right) : 0;
    }

    updateHeight(node) {
        if (node) {
        node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;
        }
    }

    rotateRight(y) {
        const x = y.left;
        const T2 = x.right;

        x.right = y;
        y.left = T2;

        this.updateHeight(y);
        this.updateHeight(x);

        return x;
    }

    rotateLeft(x) {
        const y = x.right;
        const T2 = y.left;

        y.left = x;
        x.right = T2;

        this.updateHeight(x);
        this.updateHeight(y);

        return y;
    }

    insert(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para el AVL Tree.'
        };
        }

        this.root = this.insertNode(this.root, value);
        
        return {
        success: true,
        message: `Elemento "${value}" insertado en el AVL Tree. El árbol se mantiene balanceado automáticamente.`
        };
    }

    insertNode(node, value) {
        // Inserción BST normal
        if (!node) {
        return new AVLNode(value);
        }

        if (value < node.data) {
        node.left = this.insertNode(node.left, value);
        } else if (value > node.data) {
        node.right = this.insertNode(node.right, value);
        } else {
        return node; // Duplicados no permitidos
        }

        // Actualizar altura
        this.updateHeight(node);

        // Obtener factor de balance
        const balance = this.getBalance(node);

        // Rotación derecha
        if (balance > 1 && value < node.left.data) {
        return this.rotateRight(node);
        }

        // Rotación izquierda
        if (balance < -1 && value > node.right.data) {
        return this.rotateLeft(node);
        }

        // Rotación izquierda-derecha
        if (balance > 1 && value > node.left.data) {
        node.left = this.rotateLeft(node.left);
        return this.rotateRight(node);
        }

        // Rotación derecha-izquierda
        if (balance < -1 && value < node.right.data) {
        node.right = this.rotateRight(node.right);
        return this.rotateLeft(node);
        }

        return node;
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
            ? `Elemento "${value}" encontrado en el AVL Tree.`
            : `Elemento "${value}" no encontrado en el AVL Tree.`
        };
    }

    searchNode(node, value) {
        if (!node) return false;
        
        if (value === node.data) return true;
        
        if (value < node.data) {
        return this.searchNode(node.left, value);
        } else {
        return this.searchNode(node.right, value);
        }
    }

    delete(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para eliminar.'
        };
        }

        if (!this.searchNode(this.root, value)) {
        return {
            success: false,
            message: `Elemento "${value}" no encontrado en el AVL Tree.`
        };
        }

        this.root = this.deleteNode(this.root, value);
        
        return {
        success: true,
        message: `Elemento "${value}" eliminado del AVL Tree. El balance se mantiene automáticamente.`
        };
    }

    deleteNode(node, value) {
        if (!node) return null;

        if (value < node.data) {
        node.left = this.deleteNode(node.left, value);
        } else if (value > node.data) {
        node.right = this.deleteNode(node.right, value);
        } else {
        if (!node.left || !node.right) {
            const temp = node.left || node.right;
            if (!temp) {
            node = null;
            } else {
            node = temp;
            }
        } else {
            const temp = this.findMin(node.right);
            node.data = temp.data;
            node.right = this.deleteNode(node.right, temp.data);
        }
        }

        if (!node) return node;

        this.updateHeight(node);

        const balance = this.getBalance(node);

        // Rotaciones para rebalancear
        if (balance > 1 && this.getBalance(node.left) >= 0) {
        return this.rotateRight(node);
        }

        if (balance > 1 && this.getBalance(node.left) < 0) {
        node.left = this.rotateLeft(node.left);
        return this.rotateRight(node);
        }

        if (balance < -1 && this.getBalance(node.right) <= 0) {
        return this.rotateLeft(node);
        }

        if (balance < -1 && this.getBalance(node.right) > 0) {
        node.right = this.rotateRight(node.right);
        return this.rotateLeft(node);
        }

        return node;
    }

    findMin(node) {
        while (node.left) {
        node = node.left;
        }
        return node;
    }

    inorderTraversal() {
        if (!this.root) {
        return {
            success: true,
            message: 'El AVL Tree está vacío.'
        };
        }

        const result = [];
        this.inorderHelper(this.root, result);
        
        return {
        success: true,
        message: `Recorrido In-Order del AVL Tree: [${result.join(', ')}]. El árbol está balanceado.`
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