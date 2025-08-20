import React, { useState, useContext } from "react";
import { UserContext } from "../UserContext";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function SignUp() {
    const { setUserId } = useContext(UserContext);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");


    }

    return (
        <form onSubmit={handleSubmit} style={{ textAlign: "center" }}>
            <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}></input> <br/>
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}></input> <br/>
            <button type="submit">Sign Up</button>
        </form>
    );
}

export default SignUp;