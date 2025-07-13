import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CrudArbolBinario from './pages/ArbolBinario';
import CrudSplayTree from './pages/SplayTree';
import CrudArbolB from './pages/ArbolB';
import CrudAVL from './pages/AVL';

function App() {
  return (
    <Router>
      <nav>
        <ul>
          <li><Link to="/binario">Árbol Binario</Link></li>
          <li><Link to="/splay">Splay Tree</Link></li>
          <li><Link to="/arbolb">Árbol B</Link></li>
          <li><Link to="/avl">Árbol AVL</Link></li>
        </ul>
      </nav>

      <Routes>
        <Route path="/binario" element={<CrudArbolBinario />} />
        <Route path="/splay" element={<CrudSplayTree />} />
        <Route path="/arbolb" element={<CrudArbolB />} />
        <Route path="/avl" element={<CrudAVL />} />
      </Routes>
    </Router>
  );
}

export default App;
