import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import Home from './pages/home';
import Inserciones from './pages/inserciones';
import ArbolesGuardados from './pages/guardados';
import Nosotros from './pages/nosotros';

function App() {
  return (
    <Router>
      <header></header>
        <nav>
          <ul>
            <li><Link to="/home">Home</Link></li>
            <li><Link to="/inserciones">Inserciones</Link></li>
            <li><Link to="/save">Guardados</Link></li>
            <li><Link to="/nosotros">Nosotros</Link></li>
          </ul>
        </nav>
        

      <Routes>
        <Route path="/home" element={<Home/>} />
        <Route path="/arboles" element={<Inserciones/>} />
        <Route path="/guardados" element={<ArbolesGuardados />} />
        <Route path="/nosotros" element={<Nosotros />} />
        <Route path="/inserciones/*" element={<Inserciones />} />
      </Routes>
    </Router>
  );
}

export default App;
