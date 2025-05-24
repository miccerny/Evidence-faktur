import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { apiGet } from "../utils/api";
import SellerDetails from "../components/SellerDetails";
import BuyerDeatils from "../components/BuyerDetails";

const InvoiceDetail = () => {
    // Získání parametru 'id' z URL (např. /invoices/123)
    const { id } = useParams();
    // Stav pro uložení dat faktury
    const [invoice, setInvoice] = useState();
    // Stav pro uložení dat prodejce
    const [seller, setSeller] = useState();
    // Stav pro zobrazení/skrývání detailů prodejce
    const [showSellerDetails, setSellerDetails] = useState(false);
    const [showBuyerDetails, setBuyerDeatils] = useState(false);

    // Funkce pro přepínání viditelnosti detailů prodejce
    const toggleDetails = () => {
        setSellerDetails(previousState => !previousState);
        setBuyerDeatils(previousState => !previousState);
    }

    // useEffect se spustí při načtení komponenty a kdykoliv se změní 'id'
    useEffect(() => {
        // Načtení dat faktury podle id
        apiGet("/api/invoices/" + id)
            .then(data => {
                setInvoice(data)
            })
            .catch(error => {
                console.error(error);
            });
        // Načtení dat osoby/prodejce podle id
        apiGet("/api/persons/" + id)
            .then(data => {
                setSeller(data)
            })
            .catch(error => {
                console.error(error);
            })
    }, [id]);

    // Zatímco se data nenačetla, zobrazí se text "Načítám..."
    if (!invoice) {
        return <p>Načítám...</p>
    }

    return (
        <>
            <div>
                <h1>Detail faktury</h1>
                <hr />
                <div className="container">
                    <div className="card">
                        <div className="card-title">
                            <h3>Produkt: {invoice.product}</h3>
                        </div>
                        <strong>Číslo faktury:</strong>
                        <span>{invoice.invoiceNumber} </span><br />
                        <strong>Dodavatel:</strong>
                        <br />
                        <div className="seller-box">
                            {/* Tlačítko, které přepíná viditelnost detailů prodejce */}
                            <button className="toggle-btn" onClick={toggleDetails}>
                                {invoice.seller.name}
                            </button>
                        </div>
                        {/* Detailní informace o prodejci, zobrazené/skryté podle stavu showDetails */}
                        <div className="seller-details-wrapper"></div>
                        <div className={`seller-details-content ${showSellerDetails ? 'open' : ''}`}>
                            <SellerDetails seller={invoice.seller} />
                        </div>

                        {/* Nakupující */}
                        <strong>Odběratel:</strong>
                        <br />
                        <div className="buyer-box">
                            {/* Tlačítko, které přepíná viditelnost detailů prodejce */}
                            <button className="toggle-btn" onClick={toggleDetails}>
                                {invoice.buyer.name}
                            </button>
                        </div>
                        <div className="seller-details-wrapper"></div>
                        <div className={`buyer-details-content ${showBuyerDetails ? 'open' : ''}`}>
                            <BuyerDeatils buyer={invoice.buyer} />
                        </div>


                        <p><strong>Datum vystavení faktury:</strong>
                            <br />
                            {invoice.issued}
                        </p>

                        <p><strong>Datum splatnosti faktury:</strong>
                            <br />
                            {invoice.dueDate}
                        </p>
                        <p><strong>Částka v Kč bez DPH</strong>
                            <br />
                            {invoice.price}
                        </p>
                        <p><strong>DPH v %</strong>
                            <br />
                            {invoice.vat}
                        </p>
                        <p><strong>Poznámky</strong>
                            <br />
                            {invoice.note}
                        </p>
                    </div>
                </div>
            </div>
        </>
    );




};
export default InvoiceDetail;
