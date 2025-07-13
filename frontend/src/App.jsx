import React, { useState } from 'react';
import HomePage from './pages/HomePage';
import DataStructureSimulator from './components/simulator/DataStructureSimulator';

function App() {
  const [selectedStructure, setSelectedStructure] = useState(null);

  const handleStructureSelect = (structure) => {
    setSelectedStructure(structure);
  };

  const handleBackToHome = () => {
    setSelectedStructure(null);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      {!selectedStructure ? (
        <HomePage onStructureSelect={handleStructureSelect} />
      ) : (
        <DataStructureSimulator 
          structure={selectedStructure} 
          onBack={handleBackToHome} 
        />
      )}
    </div>
  );
}

export default App;