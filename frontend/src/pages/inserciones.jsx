import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CrudArbolBinario from './Arboles/ArbolBinario';
import CrudSplayTree from './Arboles/SplayTree';
import CrudArbolB from './Arboles/ArbolB';
import CrudAVL from './Arboles/AVL';

function Inserciones() {
  return (
    <div>
      <nav>
        <ul>
          <li><Link to="/inserciones/binario">Árbol Binario</Link></li>
          <li><Link to="/inserciones/splay">Splay Tree</Link></li>
          <li><Link to="/inserciones/arbolb">Árbol B</Link></li>
          <li><Link to="/inserciones/avl">Árbol AVL</Link></li>
        </ul>
      </nav>

      <Routes>
        <Route path="/inserciones/binario" element={<CrudArbolBinario />} />
        <Route path="/inserciones/splay" element={<CrudSplayTree />} />
        <Route path="/inserciones/arbolb" element={<CrudArbolB />} />
        <Route path="/inserciones/avl" element={<CrudAVL />} />
        
      </Routes>

    </div>
  );
}

export default Inserciones;
