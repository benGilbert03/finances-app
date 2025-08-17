import React, {useState} from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './login';
import Home from './home';
import Start from './start'

function App() {
    const [userId, setUserId] = useState(-1);

    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Start/>} />
                    <Route path="/home" element={<Home
                        userId = {userId}
                        setUserId = {setUserId}
                    />} />
                    <Route path="/login" element={<Login
                        userId = {userId}
                        setUserId = {setUserId}
                    />} />
                    <Route path="/sign-up" element={<Home/>}/>
                </Routes>
            </div>
        </Router>
    )
}   

export default App;