export class Queue {
    constructor() {
        this.items = [];
    }

    async performOperation(operation, value) {
        switch (operation) {
        case 'enqueue':
            return this.enqueue(value);
        case 'dequeue':
            return this.dequeue();
        case 'front':
            return this.front();
        case 'isEmpty':
            return this.isEmpty();
        default:
            throw new Error(`Operación no soportada: ${operation}`);
        }
    }

    enqueue(element) {
        this.items.push(element);
        return {
        success: true,
        message: `Elemento "${element}" agregado al final de la cola. Tamaño actual: ${this.items.length}.`
        };
    }

    dequeue() {
        if (this.items.length === 0) {
        return {
            success: false,
            message: 'La cola está vacía. No se puede realizar la operación dequeue.'
        };
        }

        const element = this.items.shift();
        return {
        success: true,
        message: `Elemento "${element}" eliminado del frente de la cola. Tamaño actual: ${this.items.length}.`
        };
    }

    front() {
        if (this.items.length === 0) {
        return {
            success: false,
            message: 'La cola está vacía. No hay elemento al frente.'
        };
        }

        const frontElement = this.items[0];
        return {
        success: true,
        message: `El elemento al frente de la cola es: "${frontElement}".`
        };
    }

    isEmpty() {
        const empty = this.items.length === 0;
        return {
        success: true,
        message: empty ? 'La cola está vacía.' : `La cola contiene ${this.items.length} elementos.`
        };
    }

    toArray() {
        return [...this.items];
    }

    toJSON() {
        return {
        items: this.items,
        size: this.items.length
        };
    }

    toArray() {
        return [...this.items];
    }
}