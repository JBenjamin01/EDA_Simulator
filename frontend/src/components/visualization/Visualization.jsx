import React from 'react';

function Visualization({ dataStructure, structureType, isAnimating }) {
    const renderLinkedList = () => {
        const nodes = dataStructure.toArray ? dataStructure.toArray() : [];
        if (nodes.length === 0) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-24 h-24 border-4 border-dashed border-slate-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Vacía</span>
                </div>
                <p className="text-slate-500">La lista está vacía</p>
            </div>
            </div>
        );
        }

        return (
        <div className="flex items-center justify-center h-full p-8">
            <div className="flex items-center gap-4 overflow-x-auto">
            {nodes.map((node, index) => (
                <div key={index} className="flex items-center gap-2">
                <div className={`
                    w-16 h-16 rounded-xl border-2 border-blue-300 bg-blue-50 
                    flex items-center justify-center font-semibold text-blue-800
                    ${isAnimating ? 'animate-pulse' : ''}
                    shadow-lg hover:shadow-xl transition-shadow duration-200
                `}>
                    {node.data}
                </div>
                {index < nodes.length - 1 && (
                    <div className="flex items-center">
                    <div className="w-8 h-px bg-slate-400"></div>
                    <div className="w-0 h-0 border-l-4 border-l-slate-400 border-y-2 border-y-transparent"></div>
                    </div>
                )}
                </div>
            ))}
            </div>
        </div>
        );
    };

    const renderStack = () => {
        const elements = dataStructure.toArray ? dataStructure.toArray() : [];
        if (elements.length === 0) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-24 h-32 border-4 border-dashed border-slate-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Vacía</span>
                </div>
                <p className="text-slate-500">La pila está vacía</p>
            </div>
            </div>
        );
        }

        return (
        <div className="flex items-center justify-center h-full p-8">
            <div className="relative">
            <div className="text-center mb-4">
                <span className="text-sm font-medium text-slate-600 bg-slate-100 px-3 py-1 rounded-full">
                TOP
                </span>
            </div>
            <div className="flex flex-col-reverse gap-2">
                {elements.map((element, index) => (
                <div
                    key={index}
                    className={`
                    w-24 h-12 rounded-lg border-2 border-green-300 bg-green-50 
                    flex items-center justify-center font-semibold text-green-800
                    ${isAnimating && index === elements.length - 1 ? 'animate-bounce' : ''}
                    shadow-lg hover:shadow-xl transition-all duration-200
                    ${index === elements.length - 1 ? 'ring-2 ring-green-200' : ''}
                    `}
                >
                    {element}
                </div>
                ))}
            </div>
            <div className="w-28 h-2 bg-slate-300 rounded-full mt-2 -mx-2"></div>
            </div>
        </div>
        );
    };

    const renderQueue = () => {
        const elements = dataStructure.toArray ? dataStructure.toArray() : [];
        if (elements.length === 0) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-32 h-16 border-4 border-dashed border-slate-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Vacía</span>
                </div>
                <p className="text-slate-500">La cola está vacía</p>
            </div>
            </div>
        );
        }

        return (
        <div className="flex items-center justify-center h-full p-8">
            <div className="relative">
            <div className="flex justify-between mb-4 text-sm font-medium text-slate-600">
                <span className="bg-slate-100 px-3 py-1 rounded-full">FRONT</span>
                <span className="bg-slate-100 px-3 py-1 rounded-full">REAR</span>
            </div>
            <div className="flex gap-2 overflow-x-auto">
                {elements.map((element, index) => (
                <div
                    key={index}
                    className={`
                    w-16 h-16 rounded-lg border-2 border-purple-300 bg-purple-50 
                    flex items-center justify-center font-semibold text-purple-800
                    ${isAnimating ? 'animate-pulse' : ''}
                    shadow-lg hover:shadow-xl transition-shadow duration-200
                    ${index === 0 ? 'ring-2 ring-purple-200' : ''}
                    ${index === elements.length - 1 ? 'ring-2 ring-purple-200' : ''}
                    `}
                >
                    {element}
                </div>
                ))}
            </div>
            </div>
        </div>
        );
    };

    const renderTree = () => {
        const renderNode = (node, x = 400, y = 100, level = 0) => {
        if (!node) return null;

        const levelSpacing = 120;
        const horizontalSpacing = Math.max(150 - level * 20, 80);

        return (
            <g key={`${node.data}-${x}-${y}`}>
            {/* Left child connection */}
            {node.left && (
                <line
                x1={x}
                y1={y + 32}
                x2={x - horizontalSpacing}
                y2={y + levelSpacing}
                stroke="#64748b"
                strokeWidth="2"
                />
            )}
            
            {/* Right child connection */}
            {node.right && (
                <line
                x1={x}
                y1={y + 32}
                x2={x + horizontalSpacing}
                y2={y + levelSpacing}
                stroke="#64748b"
                strokeWidth="2"
                />
            )}

            {/* Node circle */}
            <circle
                cx={x}
                cy={y}
                r="24"
                fill="#fef3c7"
                stroke="#f59e0b"
                strokeWidth="2"
                className={isAnimating ? 'animate-pulse' : ''}
            />
            
            {/* Node text */}
            <text
                x={x}
                y={y + 5}
                textAnchor="middle"
                className="fill-amber-800 font-semibold text-sm"
            >
                {node.data}
            </text>

            {/* Recursively render children */}
            {node.left && renderNode(node.left, x - horizontalSpacing, y + levelSpacing, level + 1)}
            {node.right && renderNode(node.right, x + horizontalSpacing, y + levelSpacing, level + 1)}
            </g>
        );
        };

        if (!dataStructure.root) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-24 h-24 border-4 border-dashed border-slate-300 rounded-full flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Vacío</span>
                </div>
                <p className="text-slate-500">El árbol está vacío</p>
            </div>
            </div>
        );
        }

        return (
        <div className="w-full h-full overflow-auto">
            <svg width="800" height="600" className="mx-auto">
            {renderNode(dataStructure.root)}
            </svg>
        </div>
        );
    };

    const renderBTree = () => {
        if (!dataStructure.root) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-32 h-16 border-4 border-dashed border-slate-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Vacío</span>
                </div>
                <p className="text-slate-500">El Árbol B está vacío</p>
            </div>
            </div>
        );
        }

        const renderBNode = (node, x = 400, y = 100, level = 0) => {
        if (!node) return null;

        const levelSpacing = 120;
        const nodeWidth = Math.max(node.keys.length * 40, 80);
        const childSpacing = Math.max(200 - level * 30, 100);

        return (
            <g key={`${node.keys.join('-')}-${x}-${y}`}>
            {/* Connections to children */}
            {node.children && node.children.map((child, index) => {
                if (!child) return null;
                const childX = x + (index - node.children.length / 2 + 0.5) * childSpacing;
                const childY = y + levelSpacing;
                return (
                <line
                    key={`line-${index}`}
                    x1={x}
                    y1={y + 25}
                    x2={childX}
                    y2={childY - 25}
                    stroke="#64748b"
                    strokeWidth="2"
                />
                );
            })}

            {/* Node rectangle */}
            <rect
                x={x - nodeWidth / 2}
                y={y - 25}
                width={nodeWidth}
                height="50"
                fill="#e0f2fe"
                stroke="#0891b2"
                strokeWidth="2"
                rx="8"
                className={isAnimating ? 'animate-pulse' : ''}
            />

            {/* Keys */}
            {node.keys.map((key, index) => (
                <g key={`key-${index}`}>
                {index > 0 && (
                    <line
                    x1={x - nodeWidth / 2 + (index * nodeWidth) / node.keys.length}
                    y1={y - 25}
                    x2={x - nodeWidth / 2 + (index * nodeWidth) / node.keys.length}
                    y2={y + 25}
                    stroke="#0891b2"
                    strokeWidth="1"
                    />
                )}
                <text
                    x={x - nodeWidth / 2 + ((index + 0.5) * nodeWidth) / node.keys.length}
                    y={y + 5}
                    textAnchor="middle"
                    className="fill-cyan-800 font-semibold text-sm"
                >
                    {key}
                </text>
                </g>
            ))}

            {/* Recursively render children */}
            {node.children && node.children.map((child, index) => {
                if (!child) return null;
                const childX = x + (index - node.children.length / 2 + 0.5) * childSpacing;
                const childY = y + levelSpacing;
                return renderBNode(child, childX, childY, level + 1);
            })}
            </g>
        );
        };

        return (
        <div className="w-full h-full overflow-auto">
            <svg width="800" height="600" className="mx-auto">
            {renderBNode(dataStructure.root)}
            </svg>
        </div>
        );
    };

    // Función principal que decide qué renderizar
    const renderVisualization = () => {
        if (!dataStructure) {
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-24 h-24 border-4 border-dashed border-slate-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-slate-400 text-sm">Cargando...</span>
                </div>
                <p className="text-slate-500">Inicializando estructura de datos</p>
            </div>
            </div>
        );
        }

        try {
        switch (structureType) {
            case 'linked-list':
            return renderLinkedList();
            case 'stack':
            return renderStack();
            case 'queue':
            return renderQueue();
            case 'binary-tree':
            case 'bst':
            case 'avl-tree':
            case 'splay-tree':
            return renderTree();
            case 'b-tree':
            return renderBTree();
            default:
            return (
                <div className="flex items-center justify-center h-full">
                <div className="text-center">
                    <div className="w-24 h-24 border-4 border-dashed border-red-300 rounded-lg flex items-center justify-center mb-4">
                    <span className="text-red-400 text-sm">Error</span>
                    </div>
                    <p className="text-red-500">Estructura no soportada: {structureType}</p>
                </div>
                </div>
            );
        }
        } catch (error) {
        console.error('Error en visualización:', error);
        return (
            <div className="flex items-center justify-center h-full">
            <div className="text-center">
                <div className="w-24 h-24 border-4 border-dashed border-red-300 rounded-lg flex items-center justify-center mb-4">
                <span className="text-red-400 text-sm">Error</span>
                </div>
                <p className="text-red-500">Error al renderizar la estructura</p>
                <p className="text-sm text-slate-500 mt-2">{error.message}</p>
            </div>
            </div>
        );
        }
    };

    return (
        <div className="w-full h-full bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
        {renderVisualization()}
        </div>
    );
}

export default Visualization;