// Modelo de nodo para BST
class BSTNode {
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

export class BST {
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

    insert(data) {
        const value = parseInt(data);
        if (isNaN(value)) {
        return {
            success: false,
            message: 'Por favor, ingresa un número válido para el BST.'
        };
        }

        this.root = this.insertNode(this.root, value);
        
        return {
        success: true,
        message: `Elemento "${value}" insertado en el BST siguiendo la propiedad de orden.`
        };
    }

    insertNode(node, value) {
        if (!node) {
        return new BSTNode(value);
        }

        if (value < node.data) {
        node.left = this.insertNode(node.left, value);
        } else if (value > node.data) {
        node.right = this.insertNode(node.right, value);
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
            ? `Elemento "${value}" encontrado en el BST.`
            : `Elemento "${value}" no encontrado en el BST.`
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
            message: `Elemento "${value}" no encontrado en el BST.`
        };
        }

        this.root = this.deleteNode(this.root, value);
        
        return {
        success: true,
        message: `Elemento "${value}" eliminado del BST manteniendo la propiedad de orden.`
        };
    }

    deleteNode(node, value) {
        if (!node) return null;

        if (value < node.data) {
        node.left = this.deleteNode(node.left, value);
        } else if (value > node.data) {
        node.right = this.deleteNode(node.right, value);
        } else {
        // Nodo encontrado
        if (!node.left && !node.right) {
            return null;
        }
        
        if (!node.left) {
            return node.right;
        }
        
        if (!node.right) {
            return node.left;
        }
        
        // Nodo con dos hijos
        const minRight = this.findMin(node.right);
        node.data = minRight.data;
        node.right = this.deleteNode(node.right, minRight.data);
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
            message: 'El BST está vacío.'
        };
        }

        const result = [];
        this.inorderHelper(this.root, result);
        
        return {
        success: true,
        message: `Recorrido In-Order (ordenado): [${result.join(', ')}].`
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