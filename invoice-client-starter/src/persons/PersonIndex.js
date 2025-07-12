import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { apiDelete, apiGet } from "../utils/api";
import PersonTable from "./PersonTable";
import FlashMessage from "../components/FlashMessage";

const ITEMS_PER_PAGE = 5;

const PersonIndex = () => {
    // Stav pro seznam osob
    const [persons, setPersons] = useState([]);
    // Stav pro seznam osob
    const [page, setPage] = useState(0); // Stav pro stránkování
    const [totalPages, setTotalPages] = useState(1); // aktuální stránka
    const [totalElements, setTotalElements] = useState(0); // celkový počet záznamů
    const [sentState, setSent] = useState(false);
    const [sentTextState, setSentTextState] = useState("");
    const [successState, setSuccess] = useState("success");
    // Stav pro rozbalení detailní karty osoby
    const [openCardId, setOpenCardId] = useState(null);
    // Přepínání otevřeného detailu osoby (karta)
    const toggleCard = (id) => {
        setOpenCardId(previousId => (previousId === id ? null : id));
    }
    // Smazání osoby a načtení aktualizovaného seznamu
    const deletePerson = async (id) => {
        try {
            await apiDelete(`/api/persons/${id}`); // volání API pro smazání
            const data = await apiGet("/api/persons", { page, size: ITEMS_PER_PAGE }); // opětovné načtení dat po smazání
            setPersons(data.content);
            setSentTextState("Osoba byla úspěšně smazána")
            setSent(true)
            setSuccess("success");
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        } catch (error) {
            console.log(error.message);
            sentTextState("CHyba při mazání: " + error.message)
            setSent(true)
            setSuccess("danger");
            alert(error.message)
        }
    };
    // Načtení seznamu osob při změně stránky
    useEffect(() => {
        apiGet("/api/persons", { page, size: ITEMS_PER_PAGE })
            .then((data) => {
                console.log("Data z API:", data);
                setPersons(data.content);
                setTotalPages(data.totalPages);
                setTotalElements(data.totalElements);
            })
            .catch((err) => {
                console.error("Chyba při načítání osob:", err);
            });
    }, [page]); // effect se spustí vždy při změně stránky


    const sent = sentState;
    const success = successState;
    return (
        <>
        {sent && (
                <FlashMessage
                    theme={successState}
                    text={sentTextState}
                    onClose={() => setSent(false)}
                />
            )}
        <div className="container">
            <h1>Seznam osob</h1>
            <PersonTable
                totalPages={totalPages}
                setPage={setPage}
                page={page}
                deletePerson={deletePerson}
                totalElements={totalElements}
                items={persons}
                openCardId={openCardId}
                toggleCard={toggleCard}
            />
        </div>
        </>
    );
};
export default PersonIndex;
