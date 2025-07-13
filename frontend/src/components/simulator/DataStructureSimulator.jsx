import React, { useState, useEffect } from 'react';
import { ArrowLeft, Play, Pause, RotateCcw, Info } from 'lucide-react';
import ControlPanel from '../layout/ControlPanel';
import Visualization from '../visualization/Visualization';
import ExplanationPanel from '../layout/ExplanationPanel';
import { createDataStructure } from '../../utils/dataStructureFactory';

function DataStructureSimulator({ structure, onBack }) {
    const [dataStructureInstance, setDataStructureInstance] = useState(null);
    const [isAnimating, setIsAnimating] = useState(false);
    const [currentExplanation, setCurrentExplanation] = useState('');
    const [operationHistory, setOperationHistory] = useState([]);

    useEffect(() => {
        const instance = createDataStructure(structure.id);
        setDataStructureInstance(instance);
        setCurrentExplanation(`${structure.name} inicializada. Comienza agregando elementos o realizando operaciones.`);
    }, [structure]);

    const handleOperation = async (operation, value) => {
        if (!dataStructureInstance || isAnimating) return;

        setIsAnimating(true);
        
        try {
        const result = await dataStructureInstance.performOperation(operation, value);
        
        setOperationHistory(prev => [...prev, {
            operation,
            value,
            result: result.success,
            message: result.message,
            timestamp: new Date().toLocaleTimeString()
        }]);
        
        setCurrentExplanation(result.message);
        
        // Trigger re-render of visualization
        setDataStructureInstance({...dataStructureInstance});
        
        } catch (error) {
        setCurrentExplanation(`Error: ${error.message}`);
        }
        
        // Simulate animation delay
        setTimeout(() => {
        setIsAnimating(false);
        }, 1000);
    };

    const handleReset = () => {
        const instance = createDataStructure(structure.id);
        setDataStructureInstance(instance);
        setOperationHistory([]);
        setCurrentExplanation(`${structure.name} reinicializada.`);
    };

    if (!dataStructureInstance) {
        return <div className="flex items-center justify-center h-screen">Cargando...</div>;
    }

    return (
        <div className="min-h-screen flex flex-col">
        {/* Header */}
        <header className="bg-white border-b border-slate-200 px-6 py-4">
            <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
                <button
                onClick={onBack}
                className="flex items-center gap-2 px-3 py-2 text-slate-600 hover:text-slate-800 hover:bg-slate-100 rounded-lg transition-colors"
                >
                <ArrowLeft className="w-4 h-4" />
                Volver
                </button>
                <div className="h-6 w-px bg-slate-300" />
                <div className="flex items-center gap-3">
                <div className={`w-8 h-8 rounded-lg bg-gradient-to-br ${structure.color} flex items-center justify-center`}>
                    <structure.icon className="w-4 h-4 text-white" />
                </div>
                <div>
                    <h1 className="text-xl font-semibold text-slate-800">{structure.name}</h1>
                    <p className="text-sm text-slate-600">{structure.description}</p>
                </div>
                </div>
            </div>
            
            <div className="flex items-center gap-2">
                <button
                onClick={handleReset}
                className="flex items-center gap-2 px-3 py-2 text-slate-600 hover:text-slate-800 hover:bg-slate-100 rounded-lg transition-colors"
                >
                <RotateCcw className="w-4 h-4" />
                Reiniciar
                </button>
                <div className="flex items-center gap-1 px-3 py-2 bg-slate-100 rounded-lg">
                <div className={`w-2 h-2 rounded-full ${isAnimating ? 'bg-orange-500' : 'bg-green-500'}`} />
                <span className="text-sm text-slate-600">
                    {isAnimating ? 'Procesando...' : 'Listo'}
                </span>
                </div>
            </div>
            </div>
        </header>

        {/* Main Content */}
        <div className="flex-1 flex">
            {/* Control Panel */}
            <div className="w-80 bg-white border-r border-slate-200 flex flex-col">
            <ControlPanel
                structure={structure}
                onOperation={handleOperation}
                disabled={isAnimating}
                operationHistory={operationHistory}
            />
            </div>

            {/* Visualization Area */}
            <div className="flex-1 flex flex-col">
            <div className="flex-1 p-6 bg-slate-50">
                <Visualization
                dataStructure={dataStructureInstance}
                structureType={structure.id}
                isAnimating={isAnimating}
                />
            </div>
            
            {/* Explanation Panel */}
            <div className="border-t border-slate-200">
                <ExplanationPanel
                explanation={currentExplanation}
                structureName={structure.name}
                />
            </div>
            </div>
        </div>
        </div>
    );
}

export default DataStructureSimulator;