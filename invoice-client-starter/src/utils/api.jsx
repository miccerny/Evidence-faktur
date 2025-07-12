import HttpRequestError from "../errors/HttpRequestError";


const API_URL = "http://localhost:8080";

const fetchData = async (url, requestOptions = {}) => {
    const apiUrl = `${API_URL}${url}`;

    const allRequestOptions = {
        credentials: "include",
        ...requestOptions,
    };

    const response = await fetch(apiUrl, allRequestOptions);
    
    if (!response.ok) {
        const errorMessage = await response.text().catch(() =>
            `Network response was not ok: ${response.status} ${response.statusText}`
        );
        throw new HttpRequestError(errorMessage, response.status);
    }

    if (allRequestOptions.method !== 'DELETE')
        return response.json();

    return response.json();

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
