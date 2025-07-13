// src/pages/CrudArbolB.jsx
import React, { useState } from 'react';
import BotonInsertar from "@/components/BotonInsertar";
import InputNodo from "@/components/InputNodo";

export default function CrudArbolB() {
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
      <h2 className="text-xl font-bold mb-2">CRUD Árbol B</h2>
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
