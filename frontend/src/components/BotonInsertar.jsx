import React from 'react';

export default function BotonInsertar({ onClick }) {
  return (
    <button
      onClick={onClick}
      className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
    >
      Insertar Nodo
    </button>
  );
}
