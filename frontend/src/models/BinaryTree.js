// Modelo de nodo para árbol binario
class TreeNode {
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

export class BinaryTree {
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
        const newNode = new TreeNode(data);
        
        if (!this.root) {
        this.root = newNode;
        return {
            success: true,
            message: `Elemento "${data}" insertado como raíz del árbol.`
        };
        }

        // Inserción por niveles (level order)
        const queue = [this.root];
        
        while (queue.length > 0) {
        const current = queue.shift();
        
        if (!current.left) {
            current.left = newNode;
            return {
            success: true,
            message: `Elemento "${data}" insertado como hijo izquierdo de "${current.data}".`
            };
        } else if (!current.right) {
            current.right = newNode;
            return {
            success: true,
            message: `Elemento "${data}" insertado como hijo derecho de "${current.data}".`
            };
        } else {
            queue.push(current.left);
            queue.push(current.right);
        }
        }
    }

    search(data) {
        if (!this.root) {
        return {
            success: false,
            message: 'El árbol está vacío.'
        };
        }

        const queue = [this.root];
        
        while (queue.length > 0) {
        const current = queue.shift();
        
        if (current.data === data || current.data == data) {
            return {
            success: true,
            message: `Elemento "${data}" encontrado en el árbol.`
            };
        }
        
        if (current.left) queue.push(current.left);
        if (current.right) queue.push(current.right);
        }

        return {
        success: false,
        message: `Elemento "${data}" no encontrado en el árbol.`
        };
    }

    delete(data) {
        if (!this.root) {
        return {
            success: false,
            message: 'El árbol está vacío.'
        };
        }

        // Encontrar el nodo a eliminar y el último nodo
        const queue = [this.root];
        let nodeToDelete = null;
        let lastNode = null;
        
        while (queue.length > 0) {
        const current = queue.shift();
        lastNode = current;
        
        if (current.data === data || current.data == data) {
            nodeToDelete = current;
        }
        
        if (current.left) queue.push(current.left);
        if (current.right) queue.push(current.right);
        }

        if (!nodeToDelete) {
        return {
            success: false,
            message: `Elemento "${data}" no encontrado en el árbol.`
        };
        }

        // Reemplazar el valor del nodo a eliminar con el último nodo
        nodeToDelete.data = lastNode.data;
        
        // Eliminar el último nodo
        this.deleteLastNode(lastNode);
        
        return {
        success: true,
        message: `Elemento "${data}" eliminado del árbol.`
        };
    }

    deleteLastNode(lastNode) {
        const queue = [this.root];
        
        while (queue.length > 0) {
        const current = queue.shift();
        
        if (current.left === lastNode) {
            current.left = null;
            return;
        }
        
        if (current.right === lastNode) {
            current.right = null;
            return;
        }
        
        if (current.left) queue.push(current.left);
        if (current.right) queue.push(current.right);
        }
    }

    inorderTraversal() {
        if (!this.root) {
        return {
            success: true,
            message: 'El árbol está vacío.'
        };
        }

        const result = [];
        this.inorderHelper(this.root, result);
        
        return {
        success: true,
        message: `Recorrido In-Order: [${result.join(', ')}].`
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