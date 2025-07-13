// Modelo de nodo para lista enlazada
class ListNode {
    constructor(data) {
        this.data = data;
        this.next = null;
    }

    toJSON() {
        return {
        data: this.data,
        next: this.next ? this.next.toJSON() : null
        };
    }
    }

export class LinkedList {
    constructor() {
        this.head = null;
        this.size = 0;
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
        const newNode = new ListNode(data);
        
        if (!this.head) {
        this.head = newNode;
        this.size++;
        return {
            success: true,
            message: `Elemento "${data}" insertado como primer nodo de la lista.`
        };
        }

        // Insertar al final
        let current = this.head;
        while (current.next) {
        current = current.next;
        }
        current.next = newNode;
        this.size++;

        return {
        success: true,
        message: `Elemento "${data}" insertado al final de la lista. Tamaño actual: ${this.size}.`
        };
    }

    delete(data) {
        if (!this.head) {
        return {
            success: false,
            message: 'La lista está vacía. No se puede eliminar ningún elemento.'
        };
        }

        // Si el elemento a eliminar es el primero
        if (this.head.data === data || this.head.data == data) {
        this.head = this.head.next;
        this.size--;
        return {
            success: true,
            message: `Elemento "${data}" eliminado de la cabeza de la lista. Tamaño actual: ${this.size}.`
        };
        }

        let current = this.head;
        while (current.next && current.next.data !== data && current.next.data != data) {
        current = current.next;
        }

        if (current.next) {
        current.next = current.next.next;
        this.size--;
        return {
            success: true,
            message: `Elemento "${data}" encontrado y eliminado de la lista. Tamaño actual: ${this.size}.`
        };
        }

        return {
        success: false,
        message: `Elemento "${data}" no encontrado en la lista.`
        };
    }

    search(data) {
        let current = this.head;
        let position = 0;

        while (current) {
        if (current.data === data || current.data == data) {
            return {
            success: true,
            message: `Elemento "${data}" encontrado en la posición ${position}.`
            };
        }
        current = current.next;
        position++;
        }

        return {
        success: false,
        message: `Elemento "${data}" no encontrado en la lista.`
        };
    }

    display() {
        if (!this.head) {
        return {
            success: true,
            message: 'La lista está vacía.'
        };
        }

        const elements = this.toArray().map(node => node.data);
        return {
        success: true,
        message: `Lista completa: [${elements.join(' -> ')}]. Tamaño: ${this.size} elementos.`
        };
    }

    toArray() {
        const result = [];
        let current = this.head;
        while (current) {
        result.push(current);
        current = current.next;
        }
        return result;
    }

    toJSON() {
        return {
        head: this.head ? this.head.toJSON() : null,
        size: this.size
        };
    }
}