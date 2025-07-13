import { LinkedList } from '../models/LinkedList';
import { Stack } from '../models/Stack';
import { Queue } from '../models/Queue';
import { BinaryTree } from '../models/BinaryTree';
import { BST } from '../models/BST';
import { AVLTree } from '../models/AVLTree';
import { SplayTree } from '../models/SplayTree';
import { BTree } from '../models/BTree';

export function createDataStructure(type) {
    switch (type) {
        case 'linked-list':
        return new LinkedList();
        case 'stack':
        return new Stack();
        case 'queue':
        return new Queue();
        case 'binary-tree':
        return new BinaryTree();
        case 'bst':
        return new BST();
        case 'avl-tree':
        return new AVLTree();
        case 'splay-tree':
        return new SplayTree();
        case 'b-tree':
        return new BTree();
        default:
        throw new Error(`Estructura de datos no soportada: ${type}`);
    }
}