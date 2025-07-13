import React, { useState } from 'react';
import { Plus, Minus, Search, Eye, History, AlertCircle } from 'lucide-react';

function ControlPanel({ structure, onOperation, disabled, operationHistory }) {
    const [inputValue, setInputValue] = useState('');
    const [selectedOperation, setSelectedOperation] = useState(null);

    const getOperationsForStructure = (structureId) => {
        switch (structureId) {
        case 'linked-list':
            return [
            { id: 'insert', name: 'Insertar', icon: Plus, needsValue: true, color: 'blue' },
            { id: 'delete', name: 'Eliminar', icon: Minus, needsValue: true, color: 'red' },
            { id: 'search', name: 'Buscar', icon: Search, needsValue: true, color: 'green' },
            { id: 'display', name: 'Mostrar', icon: Eye, needsValue: false, color: 'purple' }
            ];
        case 'stack':
            return [
            { id: 'push', name: 'Push', icon: Plus, needsValue: true, color: 'blue' },
            { id: 'pop', name: 'Pop', icon: Minus, needsValue: false, color: 'red' },
            { id: 'peek', name: 'Peek', icon: Eye, needsValue: false, color: 'green' },
            { id: 'isEmpty', name: '¿Vacía?', icon: AlertCircle, needsValue: false, color: 'orange' }
            ];
        case 'queue':
            return [
            { id: 'enqueue', name: 'Enqueue', icon: Plus, needsValue: true, color: 'blue' },
            { id: 'dequeue', name: 'Dequeue', icon: Minus, needsValue: false, color: 'red' },
            { id: 'front', name: 'Front', icon: Eye, needsValue: false, color: 'green' },
            { id: 'isEmpty', name: '¿Vacía?', icon: AlertCircle, needsValue: false, color: 'orange' }
            ];
        case 'binary-tree':
        case 'bst':
        case 'avl-tree':
        case 'splay-tree':
            return [
            { id: 'insert', name: 'Insertar', icon: Plus, needsValue: true, color: 'blue' },
            { id: 'delete', name: 'Eliminar', icon: Minus, needsValue: true, color: 'red' },
            { id: 'search', name: 'Buscar', icon: Search, needsValue: true, color: 'green' },
            { id: 'inorder', name: 'Recorrido In-Order', icon: Eye, needsValue: false, color: 'purple' }
            ];
        case 'b-tree':
            return [
            { id: 'insert', name: 'Insertar', icon: Plus, needsValue: true, color: 'blue' },
            { id: 'delete', name: 'Eliminar', icon: Minus, needsValue: true, color: 'red' },
            { id: 'search', name: 'Buscar', icon: Search, needsValue: true, color: 'green' },
            { id: 'display', name: 'Mostrar Árbol', icon: Eye, needsValue: false, color: 'purple' }
            ];
        default:
            return [];
        }
    };

    const operations = getOperationsForStructure(structure.id);

    const handleOperationClick = async (operation) => {
        if (disabled) return;
        
        if (operation.needsValue && !inputValue.trim()) {
        alert('Por favor, ingresa un valor');
        return;
        }

        setSelectedOperation(operation.id);
        await onOperation(operation.id, operation.needsValue ? inputValue.trim() : null);
        setSelectedOperation(null);
        
        if (operation.needsValue) {
        setInputValue('');
        }
    };

    return (
        <div className="h-full flex flex-col">
        {/* Operations Section */}
        <div className="p-6 border-b border-slate-200">
            <h3 className="text-lg font-semibold text-slate-800 mb-4">Operaciones</h3>
            
            {/* Input for operations that need values */}
            <div className="mb-4">
            <label className="block text-sm font-medium text-slate-700 mb-2">
                Valor (si es necesario)
            </label>
            <input
                type="text"
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                placeholder="Ingresa un valor..."
                className="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-colors"
                disabled={disabled}
            />
            </div>

            {/* Operation Buttons */}
            <div className="grid grid-cols-1 gap-2">
            {operations.map((operation) => {
                const IconComponent = operation.icon;
                const isActive = selectedOperation === operation.id;
                const colorClasses = {
                blue: 'border-blue-200 text-blue-700 hover:bg-blue-50 hover:border-blue-300',
                red: 'border-red-200 text-red-700 hover:bg-red-50 hover:border-red-300',
                green: 'border-green-200 text-green-700 hover:bg-green-50 hover:border-green-300',
                purple: 'border-purple-200 text-purple-700 hover:bg-purple-50 hover:border-purple-300',
                orange: 'border-orange-200 text-orange-700 hover:bg-orange-50 hover:border-orange-300'
                };

                return (
                <button
                    key={operation.id}
                    onClick={() => handleOperationClick(operation)}
                    disabled={disabled || isActive}
                    className={`
                    flex items-center gap-3 w-full px-4 py-3 border-2 rounded-lg transition-all duration-200
                    ${isActive ? 'border-slate-400 bg-slate-100 text-slate-600' : colorClasses[operation.color]}
                    ${disabled ? 'opacity-50 cursor-not-allowed' : 'hover:shadow-sm'}
                    `}
                >
                    <IconComponent className="w-4 h-4" />
                    <span className="font-medium">{operation.name}</span>
                    {isActive && (
                    <div className="ml-auto w-4 h-4 border-2 border-slate-400 border-t-transparent rounded-full animate-spin" />
                    )}
                </button>
                );
            })}
            </div>
        </div>

        {/* History Section */}
        <div className="flex-1 p-6 overflow-hidden flex flex-col">
            <div className="flex items-center gap-2 mb-4">
            <History className="w-5 h-5 text-slate-600" />
            <h3 className="text-lg font-semibold text-slate-800">Historial</h3>
            </div>
            
            <div className="flex-1 overflow-y-auto space-y-2">
            {operationHistory.length === 0 ? (
                <p className="text-sm text-slate-500 text-center py-8">
                No hay operaciones realizadas aún
                </p>
            ) : (
                operationHistory.slice().reverse().map((entry, index) => (
                <div
                    key={operationHistory.length - 1 - index}
                    className={`p-3 rounded-lg border ${
                    entry.result 
                        ? 'border-green-200 bg-green-50' 
                        : 'border-red-200 bg-red-50'
                    }`}
                >
                    <div className="flex items-center justify-between mb-1">
                    <span className="font-medium text-sm text-slate-800">
                        {entry.operation}
                        {entry.value && ` (${entry.value})`}
                    </span>
                    <span className="text-xs text-slate-500">
                        {entry.timestamp}
                    </span>
                    </div>
                    <p className="text-xs text-slate-600">
                    {entry.message}
                    </p>
                </div>
                ))
            )}
            </div>
        </div>
        </div>
    );
}

export default ControlPanel;