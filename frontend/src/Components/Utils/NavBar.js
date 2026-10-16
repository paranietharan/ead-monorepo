import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

const NavBar = () => {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
      }}
    >
      <AppBar
        position="static"
        elevation={0}
        sx={{
          width: "95%",
          backgroundColor: "primary.darker",
          borderRadius: "80px",
          mt: "25px",
        }}
      >
        <Toolbar
          sx={{
            display: "flex",
            justifyContent: "space-between", // Distribute items
            paddingLeft: "16px", // Push content to the start
            paddingRight: "16px", // Add consistent spacing
          }}
        >
          {/* Logo */}
          <Typography
            variant="h6"
            sx={{
              textTransform: "uppercase",
              fontFamily: "Space Grotesk",
              flexGrow: 1,
              fontWeight: "bold",
              color: "secondary.main",
            }}
          >
            Glam
            <span style={{ textTransform: "lowercase", color: "#fff" }}>
              mod
            </span>
          </Typography>

          {/* Navigation Links */}
          <Box sx={{ display: "flex", gap: 3 }}>
            <Typography
              variant="body1"
              sx={{ color: "#fff", cursor: "pointer", fontSize: "0.9rem" }}
            >
              ABOUT US
            </Typography>
            <Typography
              variant="body1"
              sx={{ color: "#fff", cursor: "pointer", fontSize: "0.9rem" }}
            >
              SERVICES
            </Typography>
            <Typography
              variant="body1"
              sx={{ color: "#fff", cursor: "pointer", fontSize: "0.9rem" }}
            >
              PRODUCT
            </Typography>
            <Typography
              variant="body1"
              sx={{ color: "#fff", cursor: "pointer", fontSize: "0.9rem" }}
            >
              TESTIMONIALS
            </Typography>
            <Typography
              variant="body1"
              sx={{ color: "#fff", cursor: "pointer", fontSize: "0.9rem" }}
            >
              ...
            </Typography>
          </Box>

          {/* Login and Signup Buttons */}
          <Box sx={{ ml: 3, display: "flex", gap: 2 }}>
            <Button sx={{ color: "#fff", textTransform: "none" }}>Login</Button>
            <Button
              variant="outlined"
              sx={{
                color: "#fff",
                borderColor: "#fff",
                borderRadius: "30px",
                textTransform: "none",
                "&:hover": {
                  borderColor: "#32d3ac", // Green border on hover
                  color: "#32d3ac",
                },
              }}
            >
              SignUp
            </Button>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default NavBar;
