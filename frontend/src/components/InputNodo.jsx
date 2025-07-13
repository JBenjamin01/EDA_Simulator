import React from 'react';

export default function InputNodo({ valor, onCambio }) {
  return (
    <input
      type="text"
      value={valor}
      onChange={(e) => onCambio(e.target.value)}
      placeholder="Ingrese un valor"
      className="p-2 border rounded-md mr-2"
    />
  );
}
