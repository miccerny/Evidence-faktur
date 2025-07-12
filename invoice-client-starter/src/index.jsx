import { createRoot } from "react-dom/client";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { StrictMode } from "react";
import { SessionProvider } from "./contexts/session";


const root = createRoot(document.getElementById("root"));
root.render(
    <StrictMode>
        <SessionProvider>
            <App />
        </SessionProvider>
    </StrictMode>,
);
