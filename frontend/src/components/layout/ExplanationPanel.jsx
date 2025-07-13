import React from 'react';
import { Info, BookOpen } from 'lucide-react';

function ExplanationPanel({ explanation, structureName }) {
    return (
        <div className="bg-white p-6 border-t border-slate-200">
        <div className="flex items-start gap-4">
            <div className="w-10 h-10 bg-blue-100 rounded-xl flex items-center justify-center flex-shrink-0">
            <BookOpen className="w-5 h-5 text-blue-600" />
            </div>
            <div className="flex-1">
            <div className="flex items-center gap-2 mb-2">
                <h3 className="text-lg font-semibold text-slate-800">Explicación</h3>
                <span className="text-sm text-slate-500 bg-slate-100 px-2 py-1 rounded-full">
                {structureName}
                </span>
            </div>
            <p className="text-slate-700 leading-relaxed">
                {explanation}
            </p>
            </div>
        </div>
        </div>
    );
}

export default ExplanationPanel;