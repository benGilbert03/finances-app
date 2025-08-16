import React, { useState } from "react";
import axios from "axios";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userId, setUserId] = useState(-1);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!username || !password) {
      setError("Please fill in both fields.");
      return;
    }
    
    try {
      const response = await axios.post(
        "http://localhost:8080/account/login",
        { username, password } 
      );

      if (response.data > 0) {
        alert("Login successful");
        setUserId(response.data);
      } else {
        setError("Invalid username or password");
        console.log(username);
        console.log(password);
      }
    } catch (err) {
      setError("Error connecting to server")
    }
  };

  return (
      <form onSubmit={handleSubmit} style={{ textAlign: "center"}}>
        <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}></input> <br/>
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}></input> <br/>
        <button type="submit">Login</button>
        {error && <p style={{ color: "red"}}>{error}</p>}
      </form>
  );
}

function LoginSignup() {
  return (
    <div>
      <Login />
    </div>
  );
}

export default LoginSignup;
