import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Start() {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate("/login");
    } 

    const handleSignUp = () => {
        navigate("/sign-up");
    }


    return (
        <div>
            <button onClick={handleLogin}>Login</button>
            <button onClick={handleSignUp}>Sign Up</button>
        </div>
    );
}

export default Start;