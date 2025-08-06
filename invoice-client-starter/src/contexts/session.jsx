import { createContext, useContext, useEffect, useState } from "react";
import { apiGet } from "../utils/api";
import HttpRequestError from "../errors/HttpRequestError";

const SessionContext = createContext();

export function useSession() {
    return useContext(SessionContext);
}

export const SessionProvider = ({ children }) => {
    const [sessionState, setSessionState] = useState({ data: null, status: "loading" });

    useEffect(() => {
        apiGet("/api/auth")
            .then(data => {
                setSessionState({ data, status: "authenticated" });
            })
            .catch((e) => {
                if (e instanceof HttpRequestError) {
                    if (e.status === 401) {
                        setSessionState({ data: null, status: "unauthenticated" });
                        return;
                    }

                    console.warn("Chyba při volání /api/auth:", e);
                    setSessionState({ data: null, status: "error" });
                    return;
                }
                console.error("Neznámá chyba při ověřování session:", e);
                setSessionState({ data: null, status: "error" });
            });
    }, []);

    return (
        <SessionContext.Provider value={{ session: sessionState, setSession: setSessionState }}>
            {children}
        </SessionContext.Provider>
    )
}