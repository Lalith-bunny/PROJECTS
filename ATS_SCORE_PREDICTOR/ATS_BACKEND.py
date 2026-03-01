from flask import Flask, request, jsonify
from flask_cors import CORS
import fitz
import re
import nltk
import spacy
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)
CORS(app)  # Enable CORS for React

# Download stopwords once
nltk.download('stopwords')

# Load spacy model
nlp = spacy.load("en_core_web_sm")
stop_words = set(stopwords.words('english'))


# -------- TEXT EXTRACTION --------
def extract_text(file):
    text = ""
    doc = fitz.open(stream=file.read(), filetype="pdf")
    for page in doc:
        text += page.get_text()
    return text


# -------- PREPROCESSING --------
def preprocess_text(text):
    text = text.lower()
    text = re.sub(r'\S+@\S+', ' ', text)
    text = re.sub(r'http\S+', ' ', text)
    text = re.sub(r'\d+', ' ', text)
    text = re.sub(r'[^a-zA-Z\s]', ' ', text)

    doc = nlp(text)
    cleaned_tokens = []

    for token in doc:
        if token.text not in stop_words and len(token.text) > 2:
            cleaned_tokens.append(token.lemma_)

    return " ".join(cleaned_tokens)


# -------- SINGLE SCORE API --------
@app.route("/calculate", methods=["POST"])
def calculate_score():
    try:
        resume_file = request.files["resume"]
        job_desc_file = request.files["jobDescription"]

        # Extract text
        resume_text = extract_text(resume_file)
        job_desc_text = extract_text(job_desc_file)

        # Preprocess
        clean_resume = preprocess_text(resume_text)
        clean_desc = preprocess_text(job_desc_text)

        # TF-IDF Vectorization
        vectorizer = TfidfVectorizer()
        tfidf_matrix = vectorizer.fit_transform([clean_resume, clean_desc])

        # Cosine Similarity
        score = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])
        match_score = round(score[0][0] * 100, 2)
        if match_score>40:
            match_score+=30
        

        return jsonify({
           
            "score": match_score
            
        })

    except Exception as e:
        return jsonify({
            "error": str(e)
        }), 500


if __name__ == "__main__":
    app.run(port=5000)

