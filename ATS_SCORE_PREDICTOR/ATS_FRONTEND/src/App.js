import React, { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [resumeFile, setResumeFile] = useState(null);
  const [jobFile, setJobFile] = useState(null);
  const [score, setScore] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!resumeFile || !jobFile) {
      alert("Please upload both files");
      return;
    }

    const formData = new FormData();
    formData.append("resume", resumeFile);
    formData.append("jobDescription", jobFile);

    setLoading(true);

try {
  const response = await axios.post(
    "http://localhost:5000/calculate",
    formData
  );

  console.log("Backend Response:", response.data);
console.log("Score value:", response.data.score);

if (response.data && response.data.score !== undefined) {
    setScore(response.data.score);
} else {
    alert("No score returned");
}

} catch (error) {
  console.error("Frontend Error:", error);
  alert("Error connecting to backend");
}

    setLoading(false);
  };

  const getSuitabilityLevel = (score) => {
    if (score >= 80) return "Excellent Match ✅";
    if (score >= 60) return "Good Match 👍";
    if (score >= 40) return "Moderate Match ⚠";
    return "Low Match ❌";
  };

  const getColor = (score) => {
    if (score >= 80) return "#28a745";
    if (score >= 60) return "#ffc107";
    if (score >= 40) return "#fd7e14";
    return "#dc3545";
  };

  return (
    <div className="container">
      <div className="card">
        <h1>ATS Resume Scoring System</h1>

        <div className="upload-section">
          <label>Upload Resume (PDF/DOCX)</label>
          <input
            type="file"
            accept=".pdf,.docx"
            onChange={(e) => setResumeFile(e.target.files[0])}
          />
        </div>

        <div className="upload-section">
          <label>Upload Job Description (PDF/DOCX)</label>
          <input
            type="file"
            accept=".pdf,.docx"
            onChange={(e) => setJobFile(e.target.files[0])}
          />
        </div>

        <button onClick={handleSubmit}>
          {loading ? "Analyzing..." : "Calculate ATS Score"}
        </button>

        
        {score !== null && (
  <div style={{ textAlign: "center", marginTop: "20px" }}>
    <h2 style={{ color: getColor(score) }}>
      Match Score: {score}%
    </h2>
    <p style={{ color: getColor(score), fontSize: "20px" }}>
      {getSuitabilityLevel(score)}
    </p>
  </div>
)}
      </div>
    </div>
  );
}

export default App;