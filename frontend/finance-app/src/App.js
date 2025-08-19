import React, {useState} from 'react';
import { UserContext } from './UserContext';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/login';
import Home from './pages/home';
import Start from './pages/start'

function App() {
    const [userId, setUserId] = useState(-1);

    return (
        <UserContext.Provider value = {{ userId, setUserId }}>
            <Router>
                <div>
                    <Routes>
                        <Route path="/" element={<Start />} />
                        <Route path="/home" element={<Home />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/sign-up" element={<Home />}/>
                    </Routes>
                </div>
            </Router>
        </UserContext.Provider>
    );
}   

export default App;