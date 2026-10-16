import "./App.css";
import { Routes, Route } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import HomePage from "./Pages/HomePage";

import theme from "./Themes/ColorTheme";
import wishlistPage from "./Pages/WishlistPage";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <div className="App">
        <Routes>
          <Route path="/" Component={HomePage} />
          <Route path="/wishlist" Component={wishlistPage} />

        </Routes>
      </div>
    </ThemeProvider>
  );
}

export default App;
