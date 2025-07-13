// src/pages/CrudSplayTree.jsx
import React, { useState } from 'react';
import InputNodo from '../components/InputNodo';
import BotonInsertar from '../components/BotonInsertar';

export default function CrudSplayTree() {
  const [valor, setValor] = useState('');
  const [nodos, setNodos] = useState([]);

  const insertar = () => {
    if (valor.trim() !== '') {
      setNodos([...nodos, valor]);
      setValor('');
    }
  };

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">CRUD Árbol Splay</h2>
      <InputNodo valor={valor} onCambio={setValor} />
      <BotonInsertar onClick={insertar} />
      <ul className="mt-4">
        {nodos.map((nodo, idx) => (
          <li key={idx}>{nodo}</li>
        ))}
      </ul>
    </div>
  );
}
