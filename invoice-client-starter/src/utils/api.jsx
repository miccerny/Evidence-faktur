import HttpRequestError from "../errors/HttpRequestError";


const API_URL = "http://localhost:8080";


/**
 * Hlavní fetch funkce – obstarává veškeré API requesty.
 * - Umí vrátit JSON nebo text podle Content-Type
 * - Umí vyhazovat chyby jako HttpRequestError s extra daty
 */
const fetchData = async (url, requestOptions = {}) => {
    const apiUrl = `${API_URL}${url}`;
    const allRequestOptions = {
        ...requestOptions,
        credentials: "include"
        ,
        headers: {
            ...(requestOptions.headers || {}),
        },
    };
    try {
        const response = await fetch(apiUrl, allRequestOptions);

        if (!response.ok) {
            const contentType = response.headers.get("Content-Type");
            let errorData = null;
            if (contentType && contentType.includes("application/json")) {
                errorData = await response.json();

            }

            if (response.status === 401) {
                throw new HttpRequestError(errorData?.message || "Uživatel není přihlášen", 401, errorData);

            }
            if (response.status === 403) {
                throw new HttpRequestError(errorData?.message || "Nemáte oprávnění", 403, errorData);
            }

            throw new HttpRequestError(errorData?.message || "Chyba serveru", response.status, errorData);

        }
            // ✅ Úspěšná odpověď – zjistíme, jestli je JSON nebo text
            const contentType = response.headers.get("Content-Type");
            if (contentType && contentType.includes("application/json")) {
                return await response.json();
            } else {
                // fallback – pokud server pošle text
                return await response.text();
            }
        
    } catch (error) {
        console.warn("Fetch failed:", error);
        if (error instanceof HttpRequestError) {
            throw error;
        }
        throw new HttpRequestError("Neznámá chyba při volání API", 500);
    }

};

export const apiGet = async (url, params) => {
    const filteredParams = Object.fromEntries(
        Object.entries(params || {}).filter(([_, value]) => value != null)
    );

    const apiUrl = `${url}?${new URLSearchParams(filteredParams)}`;
    const requestOptions = {
        method: "GET",
    };

    const response = await fetchData(apiUrl, requestOptions);
    console.log("Odpověď z API:", response);
    return response;
};

export const apiPost = (url, data) => {
    const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    };

    return fetchData(url, requestOptions);
};

export const apiPut = (url, data) => {
    const requestOptions = {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    };

    return fetchData(url, requestOptions);
};

export const apiDelete = (url) => {
    const requestOptions = {
        method: "DELETE",
    };

    return fetchData(url, requestOptions);
};
