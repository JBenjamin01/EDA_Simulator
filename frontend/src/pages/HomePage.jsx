import React from 'react';
import { List, FileStack as Stack, Layers, GitBranch, TreePine, ArrowRight, BookOpen, Scale, Shuffle, Database } from 'lucide-react';

const dataStructures = [
    {
        id: 'linked-list',
        name: 'Lista Enlazada',
        description: 'Secuencia de nodos conectados dinámicamente',
        icon: List,
        color: 'from-blue-500 to-blue-600',
        operations: ['Insertar', 'Eliminar', 'Buscar', 'Recorrer']
    },
    {
        id: 'stack',
        name: 'Pila (Stack)',
        description: 'Estructura LIFO - Último en entrar, primero en salir',
        icon: Stack,
        color: 'from-green-500 to-green-600',
        operations: ['Push', 'Pop', 'Peek', 'Verificar vacía']
    },
    {
        id: 'queue',
        name: 'Cola (Queue)',
        description: 'Estructura FIFO - Primero en entrar, primero en salir',
        icon: Layers,
        color: 'from-purple-500 to-purple-600',
        operations: ['Enqueue', 'Dequeue', 'Front', 'Verificar vacía']
    },
    {
        id: 'binary-tree',
        name: 'Árbol Binario',
        description: 'Estructura jerárquica con máximo 2 hijos por nodo',
        icon: GitBranch,
        color: 'from-orange-500 to-orange-600',
        operations: ['Insertar', 'Recorrido', 'Buscar', 'Eliminar']
    },
    {
        id: 'bst',
        name: 'Árbol Binario de Búsqueda',
        description: 'Árbol binario ordenado para búsquedas eficientes',
        icon: TreePine,
        color: 'from-red-500 to-red-600',
        operations: ['Insertar', 'Buscar', 'Eliminar', 'Recorridos']
    },
    {
        id: 'avl-tree',
        name: 'Árbol AVL',
        description: 'Árbol binario auto-balanceado con rotaciones automáticas',
        icon: Scale,
        color: 'from-indigo-500 to-indigo-600',
        operations: ['Insertar', 'Eliminar', 'Buscar', 'Rotaciones', 'Balance']
    },
    {
        id: 'splay-tree',
        name: 'Splay Tree',
        description: 'Árbol auto-ajustable que mueve elementos accedidos a la raíz',
        icon: Shuffle,
        color: 'from-pink-500 to-pink-600',
        operations: ['Insertar', 'Buscar', 'Eliminar', 'Splay', 'Rotaciones']
    },
    {
        id: 'b-tree',
        name: 'Árbol B',
        description: 'Árbol balanceado de múltiples vías optimizado para almacenamiento',
        icon: Database,
        color: 'from-teal-500 to-teal-600',
        operations: ['Insertar', 'Eliminar', 'Buscar', 'Dividir', 'Fusionar']
    }
];

function HomePage({ onStructureSelect }) {
    return (
        <div className="min-h-screen flex flex-col">
        {/* Header */}
        <header className="bg-white/80 backdrop-blur-sm border-b border-slate-200 px-6 py-4">
            <div className="max-w-7xl mx-auto flex items-center gap-3">
            <div className="w-10 h-10 bg-gradient-to-br from-blue-600 to-purple-600 rounded-xl flex items-center justify-center">
                <BookOpen className="w-6 h-6 text-white" />
            </div>
            <div>
                <h1 className="text-2xl font-bold text-slate-800">EDA - Simulator</h1>
                <p className="text-sm text-slate-600">Un Simulador Visual de Estructuras de Datos</p>
            </div>
            </div>
        </header>

        {/* Main Content */}
        <main className="flex-1 px-6 py-12">
            <div className="max-w-7xl mx-auto">
            {/* Hero Section */}
            <div className="text-center mb-16">
                <h2 className="text-4xl font-bold text-slate-800 mb-4">
                Aprende Estructuras de Datos de Forma Visual
                </h2>
                <p className="text-xl text-slate-600 max-w-3xl mx-auto">
                Experimenta con las estructuras de datos más importantes a través de simulaciones 
                interactivas y visualizaciones en tiempo real.
                </p>
            </div>

            {/* Data Structures Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {dataStructures.map((structure) => {
                const IconComponent = structure.icon;
                return (
                    <div
                    key={structure.id}
                    className="group bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 cursor-pointer border border-slate-100 hover:border-slate-200"
                    onClick={() => onStructureSelect(structure)}
                    >
                    <div className="flex items-center justify-between mb-4">
                        <div className={`w-12 h-12 rounded-xl bg-gradient-to-br ${structure.color} flex items-center justify-center group-hover:scale-110 transition-transform duration-200`}>
                        <IconComponent className="w-6 h-6 text-white" />
                        </div>
                        <ArrowRight className="w-5 h-5 text-slate-400 group-hover:text-slate-600 group-hover:translate-x-1 transition-all duration-200" />
                    </div>
                    
                    <h3 className="text-xl font-semibold text-slate-800 mb-2">
                        {structure.name}
                    </h3>
                    
                    <p className="text-slate-600 mb-4 text-sm leading-relaxed">
                        {structure.description}
                    </p>
                    
                    <div className="space-y-2">
                        <p className="text-xs font-medium text-slate-500 uppercase tracking-wide">
                        Operaciones disponibles:
                        </p>
                        <div className="flex flex-wrap gap-1">
                        {structure.operations.map((operation, index) => (
                            <span
                            key={index}
                            className="px-2 py-1 bg-slate-100 text-slate-600 text-xs rounded-lg"
                            >
                            {operation}
                            </span>
                        ))}
                        </div>
                    </div>
                    </div>
                );
                })}
            </div>

            {/* Features Section */}
            <div className="mt-20 grid grid-cols-1 md:grid-cols-3 gap-8">
                <div className="text-center">
                <div className="w-16 h-16 bg-blue-100 rounded-2xl flex items-center justify-center mx-auto mb-4">
                    <GitBranch className="w-8 h-8 text-blue-600" />
                </div>
                <h3 className="text-lg font-semibold text-slate-800 mb-2">Visualización Interactiva</h3>
                <p className="text-slate-600 text-sm">
                    Observa cada operación paso a paso con animaciones fluidas y claras.
                </p>
                </div>
                
                <div className="text-center">
                <div className="w-16 h-16 bg-green-100 rounded-2xl flex items-center justify-center mx-auto mb-4">
                    <BookOpen className="w-8 h-8 text-green-600" />
                </div>
                <h3 className="text-lg font-semibold text-slate-800 mb-2">Explicaciones Detalladas</h3>
                <p className="text-slate-600 text-sm">
                    Aprende con explicaciones en tiempo real de cada operación y concepto.
                </p>
                </div>
                
                <div className="text-center">
                <div className="w-16 h-16 bg-purple-100 rounded-2xl flex items-center justify-center mx-auto mb-4">
                    <Layers className="w-8 h-8 text-purple-600" />
                </div>
                <h3 className="text-lg font-semibold text-slate-800 mb-2">Múltiples Estructuras</h3>
                <p className="text-slate-600 text-sm">
                    Explora diferentes estructuras de datos y comprende sus casos de uso.
                </p>
                </div>
            </div>
            </div>
        </main>
        </div>
    );
}

export default HomePage;