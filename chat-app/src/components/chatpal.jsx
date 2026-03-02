import { useState, useEffect, useRef } from "react";

// All the keywords and replies the bot knows about
const knowledge = {
  hello: {
    triggers: ["hi", "hello", "hey", "howdy", "what's up", "sup"],
    replies: [
      "Hey! Nice to meet you 😊",
      "Hello there! How's your day going?",
      "Hi! I'm glad you're here 👋",
    ],
  },
  identity: {
    triggers: ["who are you", "what are you", "your name", "introduce yourself"],
    replies: [
      "I'm ChatPal 🤖 — a simple chatbot built with React!",
      "My name is ChatPal! I was created using React hooks and some JavaScript logic.",
    ],
  },
  feeling: {
    triggers: ["how are you", "you okay", "how do you feel", "you good"],
    replies: [
      "I'm just code, but I feel amazing! 😄",
      "Doing well! Ready to chat with you.",
      "All good on my end! What about you?",
    ],
  },
  skills: {
    triggers: ["what can you do", "your skills", "help", "capabilities", "what do you know"],
    replies: [
      "Here's what I can help with! 😊\n\n👋 Greet you — try: hi, hello, hey\n🤖 Tell you about myself — try: who are you\n😄 Crack a joke — try: tell me a joke\n💬 Small talk — try: how are you\n📚 Fun facts — try: give me a fact\n👋 Say goodbye — try: bye",
    ],
  },
  jokes: {
    triggers: ["joke", "make me laugh", "funny", "tell me a joke", "humor"],
    replies: [
      "Why did the developer go broke? Because he used up all his cache! 😂",
      "What do you call a fish with no eyes? A fsh! 🐟😄",
      "Why did the scarecrow win an award? Because he was outstanding in his field! 🌾😆",
      "I asked my dog what 2 minus 2 is. He said nothing. 🐶😄",
    ],
  },
  facts: {
    triggers: ["fact", "give me a fact", "something interesting", "did you know"],
    replies: [
      "Did you know? Honey never expires. Archaeologists found 3000-year-old honey in Egyptian tombs! 🍯",
      "Fun fact: Octopuses have three hearts and blue blood! 🐙",
      "Cool fact: A group of flamingos is called a 'flamboyance'! 🦩",
      "Did you know? Bananas are technically berries, but strawberries are not! 🍌",
    ],
  },
  thanks: {
    triggers: ["thanks", "thank you", "appreciate it", "thx"],
    replies: [
      "No problem at all! 😊",
      "Glad I could help!",
      "Anytime! That's what I'm here for.",
    ],
  },
  goodbye: {
    triggers: ["bye", "goodbye", "see you", "take care", "later", "cya"],
    replies: [
      "Goodbye! Come back soon 👋",
      "See you later! Take care 😊",
      "Bye! It was great chatting with you!",
    ],
  },
};

// This function checks the user message and finds a matching reply
function findReply(userMessage) {
  const msg = userMessage.toLowerCase();

  for (const topic of Object.values(knowledge)) {
    const matched = topic.triggers.some((word) => msg.includes(word));
    if (matched) {
      const allReplies = topic.replies;
      const randomIndex = Math.floor(Math.random() * allReplies.length);
      return allReplies[randomIndex];
    }
  }

  // Default reply if nothing matched
  return "Hmm, I didn't quite get that 🤔 Try asking me what I can do!";
}

// Single message bubble component
function MessageBubble({ message }) {
  const isUser = message.sender === "user";

  const bubbleStyle = {
    maxWidth: "68%",
    padding: "10px 14px",
    borderRadius: isUser ? "16px 16px 4px 16px" : "16px 16px 16px 4px",
    fontSize: "14px",
    lineHeight: "1.6",
    whiteSpace: "pre-line",
    background: isUser ? "#4f46e5" : "#f1f1f1",
    color: isUser ? "#fff" : "#333",
    alignSelf: isUser ? "flex-end" : "flex-start",
  };

  return <div style={bubbleStyle}>{message.text}</div>;
}

// Main chatbot component
export default function ChatPal() {
  const [chatHistory, setChatHistory] = useState([
    { id: 1, sender: "bot", text: "Hey there! I'm ChatPal 🤖 Ask me what I can do!" },
  ]);
  const [userInput, setUserInput] = useState("");
  const [botIsTyping, setBotIsTyping] = useState(false);

  const chatEndRef = useRef(null);

  // Auto scroll to latest message
  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [chatHistory, botIsTyping]);

  function handleSend() {
    const trimmed = userInput.trim();
    if (!trimmed) return;

    // Add user message to chat
    const userMsg = { id: Date.now(), sender: "user", text: trimmed };
    setChatHistory((prev) => [...prev, userMsg]);
    setUserInput("");
    setBotIsTyping(true);

    // Simulate bot thinking delay
    setTimeout(() => {
      const botReply = findReply(trimmed);
      const botMsg = { id: Date.now() + 1, sender: "bot", text: botReply };
      setChatHistory((prev) => [...prev, botMsg]);
      setBotIsTyping(false);
    }, 1000);
  }

  function handleKeyPress(e) {
    if (e.key === "Enter") handleSend();
  }

  return (
    <div style={{ width: "100vw", height: "100vh", background: "#eef2ff" }}>
     <div style={{
  width: "100%",
  height: "100%",
  background: "#fff",
  display: "flex",
  flexDirection: "column",
  overflow: "hidden"
}}>

        {/* Top bar */}
        <div style={{ background: "#4f46e5", padding: "16px 20px", color: "#fff" }}>
          <div style={{ fontWeight: "700", fontSize: "17px" }}>🤖 ChatPal</div>
          <div style={{ fontSize: "12px", marginTop: "2px", opacity: 0.8 }}>● Always online</div>
        </div>

        {/* Chat messages area */}
        <div style={{ flex: 1, overflowY: "auto", padding: "16px", display: "flex", flexDirection: "column", gap: "10px" }}>
          {chatHistory.map((msg) => (
            <MessageBubble key={msg.id} message={msg} />
          ))}

          {/* Typing indicator */}
          {botIsTyping && (
            <div style={{ fontSize: "13px", color: "#999", paddingLeft: "4px" }}>
              ChatPal is typing...
            </div>
          )}

          <div ref={chatEndRef} />
        </div>

        {/* Input area */}
        <div style={{ display: "flex", gap: "8px", padding: "12px", borderTop: "1px solid #eee" }}>
          <input
            type="text"
            value={userInput}
            onChange={(e) => setUserInput(e.target.value)}
            onKeyDown={handleKeyPress}
            placeholder="Say something..."
            style={{ flex: 1, padding: "10px 14px", borderRadius: "8px", border: "1px solid #ddd", fontSize: "14px", outline: "none" }}
          />
          <button
            onClick={handleSend}
            style={{ background: "#4f46e5", color: "#fff", border: "none", borderRadius: "8px", padding: "10px 18px", fontSize: "14px", cursor: "pointer" }}
          >
            Send
          </button>
        </div>

      </div>
    </div>
  );
}
