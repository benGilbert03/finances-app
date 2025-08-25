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

        if (!username || !password) {
            setError("Please fill in both fields.");
            return;
        }

        try {
            const response = await axios.post(
                "http://localhost:8080/account",
                { username, password }
            );

            if (response.data > 0) {
                setUserId(response.data);
                navigate("/home")
            } else {
                setError("That username has already been taken");
            }
        } catch (err) {
            setError("Error connecting to server");
        }
    }

    const goToLogin = (e) => {
        navigate("/login")
    }

    return (
        <div>
            <form onSubmit={handleSubmit} style={{ textAlign: "center" }}>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}></input> <br/>
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}></input> <br/>
                <button type="submit">Sign Up</button> <br/>
                <button onClick={goToLogin}>Go to Login</button>
                {error && <p style = {{ color:"red"}}>{error}</p>} 
            </form>
            
        </div>
    );
}

export default SignUp;