export class Stack {
    constructor() {
        this.items = [];
    }

    async performOperation(operation, value) {
        switch (operation) {
        case 'push':
            return this.push(value);
        case 'pop':
            return this.pop();
        case 'peek':
            return this.peek();
        case 'isEmpty':
            return this.isEmpty();
        default:
            throw new Error(`Operación no soportada: ${operation}`);
        }
    }

    push(element) {
        this.items.push(element);
        return {
        success: true,
        message: `Elemento "${element}" agregado al tope de la pila. Tamaño actual: ${this.items.length}.`
        };
    }

    pop() {
        if (this.items.length === 0) {
        return {
            success: false,
            message: 'La pila está vacía. No se puede realizar la operación pop.'
        };
        }

        const element = this.items.pop();
        return {
        success: true,
        message: `Elemento "${element}" eliminado del tope de la pila. Tamaño actual: ${this.items.length}.`
        };
    }

    peek() {
        if (this.items.length === 0) {
        return {
            success: false,
            message: 'La pila está vacía. No hay elemento en el tope.'
        };
        }

        const topElement = this.items[this.items.length - 1];
        return {
        success: true,
        message: `El elemento en el tope de la pila es: "${topElement}".`
        };
    }

    isEmpty() {
        const empty = this.items.length === 0;
        return {
        success: true,
        message: empty ? 'La pila está vacía.' : `La pila contiene ${this.items.length} elementos.`
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