import React, { useState, useContext } from "react";
import { UserContext } from "../UserContext";
import axios from "axios";

function Home() {
    const { userId } = useContext(UserContext);

    return (
        <div>
            <h1>Home Page</h1>
            {userId > 0 ? (
                <p>You are logged in with ID: {userId}</p>
            ) : (
                <p>You are not logged in</p>
            )}  
        </div>
    )
}

export default Home;