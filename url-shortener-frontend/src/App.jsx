import { useState } from "react";
import { createShortUrl } from "./api/api";
import './App.css';
import "@fortawesome/fontawesome-free/css/all.min.css";


function App() {
  const [longUrl, setLongUrl] = useState("");
  const [customShortCode, setCustomShortCode] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [error, setError] = useState("");
  const [copySuccess, setCopySuccess] = useState(false);
  const BASE_URL = "http://localhost:8082";

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    try {
      const response = await createShortUrl(longUrl, customShortCode);
      setShortUrl(`${BASE_URL}/${response.shortCode}`);
    } catch (err) {
      console.error("Error creating short URL:", err);

      // Handle different error types
      if (err.response) {
        // Server responded with an error
        setError(err.response.data);
      } else if (err.request) {
        // No response from server
        setError("No response from server. Please try again later.");
      } else {
        // Other errors (e.g., network issues)
        setError("Something went wrong. Please try again.");
      }
    }
    
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(shortUrl)
      .then(() => {
        setCopySuccess(true);
        setTimeout(() => setCopySuccess(false), 2000); // reset message after 2 seconds
      })
      .catch((err) => {
        console.error("Error copying to clipboard:", err);
        setError("Error copying to clipboard. Please try again.");
      });
  };


  
  return (
    <div className="container">
      <div className="box">
        <h1>URL Shortener</h1>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <input
              type="url"
              className="input"
              placeholder="Enter long URL"
              value={longUrl}
              onChange={(e) => setLongUrl(e.target.value)}
            />
          </div>
          <div className="input-group">
            <input
              type="text"
              className="input"
              placeholder="Custom short code (optional)"
              value={customShortCode}
              onChange={(e) => setCustomShortCode(e.target.value)}
            />
          </div>
          <button type="submit" className="button" disabled={!longUrl.trim()}>
            Shorten
          </button>
        </form>

        {error && <div className="error">{error}</div>}

        {shortUrl && (
          <div className="short-url">
            <h3>Shortened URL:</h3>
            <p>{shortUrl}</p>
            <button onClick={handleCopy} style={{ background: "none", border: "none", cursor: "pointer", color:"grey" }}>
              <i className="fas fa-clipboard fa-lg"></i> {/* Clipboard icon */}
            </button>
            {copySuccess && <span style={{ marginLeft: "10px", color: "green" }}>Copied!</span>}
          </div>
        )}
      </div>
    </div>
  );
  
  
}

export default App;


