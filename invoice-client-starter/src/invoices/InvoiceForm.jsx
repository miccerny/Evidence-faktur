import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"
import { apiGet, apiPost, apiPut } from "../utils/api";
import FlashMessage from "../components/FlashMessage";
import InputField from "../components/InputField";
import InputSelect from "../components/InputSelect";
import "bootstrap/dist/css/bootstrap.min.css";


const InvoiceForm = ({existingInvoiceData}) => {
    // Používáme hook pro navigaci mezi stránkami
    const navigate = useNavigate();
    // Získáme parametr "id" z URL, pokud je při úpravě faktury
    const { id } = useParams();
    // Stav pro seznam možných odběratelů
    const [buyerListState, setBuyerList] = useState([]);
    // Stav pro seznam možných dodavatelů 
    const [sellerListState, setSellerList] = useState([]);
    // Stav pro data faktury (počáteční prázdné hodnoty)
    const [invoice, setInvoice] = useState({
        invoiceNumber: "",
        seller: "",
        buyer: "",
        issued: "",
        dueDate: "",
        product: "",
        price: "",
        vat: "",
        note: ""
    });
    // Stav pro informace o tom, jestli už byla faktura odeslána
    const [sentState, setSent] = useState(false);
    // Stav pro informaci, jestli uložení faktury bylo úspěšné
    const [successState, setSuccess] = useState(false);
    // Stav pro uložení chybové zprávy při selhání uložení
    const [errorState, setError] = useState(null);
    const today = new Date().toISOString().split('T')[0]; 

    // useEffect se spustí při načtení komponenty nebo změně id
    useEffect(() => {
        if (id) {
            // Pokud máme id, načteme data existující faktury z API
            apiGet("/api/invoices/" + id)
                .then((data) => {
                    setInvoice(data) // nastavíme data do stavu
                });
        }
        // Načteme seznam osob (odběratelé i dodavatelé)
        apiGet("/api/persons").then((data) => {
            setBuyerList(data.content),
                setSellerList(data.content) // tady nastavujeme oba seznamy na stejná data
        });
    }, [id]);


    // Funkce pro odeslání formuláře
    const handleSubmit = (e) => {
        e.preventDefault(); // zabráníme defaultnímu reloadu stránky

        // jednoduchá validace datumu
        if (invoice.issued > invoice.dueDate) {
            setError("Datum vystavení nemůže být pozdější než datum splatnosti");
            setSent(true);
            setSuccess(false);
            return;  // neodesílat data
        }

        if(invoice.seller == invoice.buyer){
            setError("Odběratel nesmí být stejný jako dodavatel");
            setSent(true);
            setSuccess(false);
            return;  // neodesílat data
        }

        // Pokud je id, aktualizujeme fakturu (PUT), jinak vytvoříme novou (POST)
        (id ? apiPut("/api/invoices/" + id, invoice) : apiPost("/api/invoices", invoice))
            .then((data) => {
                setSent(true); // označíme, že jsme odeslali data
                setSuccess(true);  // označíme úspěch
                navigate("/invoices"); // přesměrujeme zpět na seznam faktur
            })
            .catch((error) => {
                console.log(error.message); // vypíšeme chybu do konzole
                setError(error.message);  // uložíme chybovou zprávu
                setSent(true); // odeslání proběhlo, ale neúspěšně
                setSuccess(false);
            });
    };

    const sent = sentState;
    const success = successState;

    return (
        <div className="container mb-3">
            <h1>{id ? "Upravit" : "Vytvořit"} fakturu</h1>
            <hr />
            {/* Pokud máme chybu, zobrazíme ji */}
            {errorState ? (
                <div className="alert alert-danger"></div>
            ) : null}
            {/* FlashMessage se zobrazí po odeslání, podle výsledku nastavíme barvu a text */}
            {sent && (
                <FlashMessage
                    theme={success ? "success" : ""}
                    text={success ? "Uložení faktury proběhlo úspěšně" : ""}
                />
            )}
            {/* Formulář pro zadání faktury */}
            <form onSubmit={handleSubmit}>
                {/* Číslo faktury */}
                <div className="mt-3">
                    <InputField
                        required={true}
                        type="number"
                        name="invoiceNumber"
                        min="3"
                        label="Číslo faktury"
                        prompt="Zadej číslo"
                        value={invoice.invoiceNumber}
                        handleChange={(e) => {
                            setInvoice({ ...invoice, invoiceNumber: e.target.value });
                        }}
                    />
                </div>
                {/* Výběr dodavatele */}
                <InputSelect
                    required={true}
                    className="browser-default form-select"
                    name="seller"
                    label="Dodavatel"
                    handleChange={(e) => {
                        setInvoice({ ...invoice, seller: { _id: e.target.value } });
                    }}
                    value={invoice.seller.name}
                    items={sellerListState}
                />
                {/* Výběr odběratele */}
                <InputSelect
                    required={true}
                    className="browser-default form-select"
                    name="buyer"
                    label="Odběratel"
                    handleChange={(e) => {
                        setInvoice({ ...invoice, buyer: { _id: e.target.value } });
                    }}
                    value={invoice.buyer.name}
                    items={buyerListState}
                />
                {/* Datum vystavení faktury */}
                <InputField
                    required={true}
                    type="date"
                    name="issued"
                    min="3"
                    label="Datum vystavení faktury"
                    prompt="Zadejte číslo faktury"
                    value={invoice.issued}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, issued: e.target.value });
                    }}
                />
                {/* Datum splatnosti faktury */}
                <InputField
                    required={true}
                    type="date"
                    name="dueDate"
                    min="3"
                    label="Datum splatnosti faktury"
                    prompt="Zadej datum"
                    value={invoice.dueDate}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, dueDate: e.target.value });
                    }}
                />
                {/* Název produktu */}
                <InputField
                    required={true}
                    type="text"
                    name="product"
                    min="3"
                    label="Jméno produktu"
                    prompt="Zadej název produktu"
                    value={invoice.product}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, product: e.target.value });
                    }}
                />
                {/* Cena faktury */}
                <InputField
                    required={true}
                    type="number"
                    name="price"
                    min="3"
                    label="Částka"
                    prompt="Zadej částku např. 1230"
                    value={invoice.price}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, price: e.target.value });
                    }}
                />
                {/* DPH v procentech */}
                <InputField
                    required={true}
                    type="number"
                    name="vat"
                    min="3"
                    label="DPH v %"
                    prompt="Zadej DPH"
                    value={invoice.vat}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, vat: e.target.value });
                    }}
                />
                {/* Volitelná poznámka */}
                <InputField
                    required={false}
                    type="textarea"
                    name="note"
                    min="3"
                    label="Zadejte poznámku"
                    prompt="Poznámka..."
                    value={invoice.note}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, note: e.target.value });
                    }}
                />
                {/* Tlačítko pro odeslání formuláře */}
                <input type="submit" className="btn btn-primary mt-3" value={"Uložit"} />
            </form>
        </div>
    );
};
export default InvoiceForm;