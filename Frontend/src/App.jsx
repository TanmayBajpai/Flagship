import RegisterPage from "./Pages/RegisterPage"
import HomePage from "./Pages/HomePage"
import LoginPage from "./Pages/LoginPage"
import {Routes, Route} from "react-router-dom"

function App() {
  return (
    <>  
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </>
  )
}

export default App