import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";
import FlashMessage from "../components/FlashMessage";
import {useSession} from "../contexts/session";


const ITEMS_PER_PAGE = 5;

const InvoiceIndex = () => {
    // Stav pro seznam faktur
    const [invoices, setInvoices] = useState([]);
    const {session} = useSession();
    const [loading, setLoading] = useState(false);
    // Stav pro seznam dodavatelů (pro filtr)
    const [sellerListSatte, setSellerList] = useState([]);
    // Stav pro seznam odběratelů (pro filtr)
    const [buyerListState, setBuyerList] = useState([]);
    // Stav pro seznam produktů
    const [productListState] = useState([]);
    // Stav pro aktuální stránku stránkování
    const [page, setPage] = useState(0);
    // Celkový počet stránek (dle dat z backendu)
    const [totalPages, setTotalPages] = useState(1);
    // Celkový počet položek (dle dat z backendu)
    const [totalElements, setTotalElements] = useState(0);
    // Stav pro aktuální hodnoty filtru (dodavatel, odběratel, produkt, ceny, limit)
    const [sentState, setSent] = useState("success");
    const [sentTextState, setSentTextState] = useState("");
    const [successState, setSuccess] = useState(false);
    const [filterState, setFilter] = useState({
        sellerID: undefined,
        buyerID: undefined,
        product: undefined,
        minPrice: undefined,
        maxPrice: undefined,
        limit: undefined,
    });
    // Stav pro ID otevřené karty detailu faktury (umožňuje rozbalení / sbalení)
    const [openCardId, setOpenCardId] = useState(null);
    // Funkce pro přepnutí otevření detailu konkrétní faktury
    const toggleCard = (id) => {
        setOpenCardId(previousId => (previousId === id ? null : id));
    }
    // Funkce pro smazání faktury podle ID
    const deleteInvoice = async (id) => {
        try {
            await apiDelete("/api/invoices/" + id); // Volání API pro smazání faktury
            // Po smazání faktury načteme aktualizovaný seznam faktur z API
            const data = await apiGet("/api/invoices", { page, size: ITEMS_PER_PAGE });
            setInvoices(data.content);
            setSentTextState("Faktura byla úspěšně smazána")
            setSent(true)
            setSuccess("success");
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        } catch (error) {
            console.log(error.message);
            sentTextState("Chyba při mazání: " + error.message)
            setSent(true)
            setSuccess("danger");
            alert(error.message);
        }
        // Okamžitě odstraníme fakturu i z lokálního stavu pro rychlejší odezvu UI
        setInvoices(invoices.filter((item) => item._id !== id));
    };

    // useEffect načte faktury a osoby při změně stránky
    useEffect(() => {
        if(session.status === "authenticated"){
            setLoading(true);
        
        // Načtení faktur s parametry stránkování
        apiGet("/api/invoices", { page, size: 1000 })
            .then((data) => {
                setInvoices(data.content);
                setTotalPages(data.totalPages);
                setTotalElements(data.totalElements);
            });
        apiGet("/api/persons").then((data) => {
            console.log("Persons:", data);
            setSellerList(data.content);
            setBuyerList(data.content);
        })
        .finally(() => setLoading(false));
    }
    
    }, [page, session]);

    // Funkce pro změnu hodnoty ve filtru (při změně vstupního pole)
    const handleChange = (e) => {
        if (e.target.value === "false" || e.target.value === "true" || e.target.value === '') {
            // Pokud je hodnota "false", "true" nebo prázdná, nastavíme hodnotu filtru na undefined (vymažeme filtr)
            setFilter(prevState => {
                return { ...prevState, [e.target.name]: undefined }
            });
        } else {
            // Jinak uložíme novou hodnotu do stavu filtru
            setFilter(prevState => {
                return { ...prevState, [e.target.name]: e.target.value }
            });
        }
    };

    // Funkce pro odeslání filtru formuláře
    const handleSubmit = async (e) => {
        e.preventDefault();
        const params = filterState; // Vezmeme aktuální hodnoty filtru
        const data = await apiGet("/api/invoices", params); // Požádáme API o vyfiltrované faktury
        setInvoices(data.content); // Nastavíme vyfiltrované faktury do stavu
    }
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

                <h1>Seznam faktur</h1>
                <hr />
                <InvoiceFilter
                    handleChange={handleChange}
                    handleSubmit={handleSubmit}
                    sellerList={sellerListSatte}
                    buyerList={buyerListState}
                    product={productListState}
                    filter={filterState}
                    confirm="Filtrovat faktury"
                />
                <hr />
                <InvoiceTable
                    deleteInvoice={deleteInvoice}
                    items={invoices}
                    totalElements={totalElements}
                    totalPages={totalPages}
                    setPage={setPage}
                    page={page}
                    openCardId={openCardId}
                    toggleCard={toggleCard}
                    label="Počet faktur:"
                />
            </div>
        </>
    );
};
export default InvoiceIndex;