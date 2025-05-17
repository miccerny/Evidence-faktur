import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import { apiGet } from "../utils/api";
import SellerDetails from "../components/SellerDetails";

const InvoiceDetail = () => {
    // Získání parametru 'id' z URL (např. /invoices/123)
    const { id } = useParams();
    // Stav pro uložení dat faktury
    const [invoice, setInvoice] = useState();
    // Stav pro uložení dat prodejce
    const [seller, setSeller] = useState();
    // Stav pro zobrazení/skrývání detailů prodejce
    const [showDetails, setShowDetails] = useState(false);

    // Funkce pro přepínání viditelnosti detailů prodejce
    const toggleDetails = () => {
        setShowDetails(previousState => !previousState);
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
                        <br />
                        <span>{invoice.invoiceNumber} </span><br />
                        <p><strong>Dodavatel:</strong></p>
                        <br />
                        <div className="seller-box">
                            {/* Tlačítko, které přepíná viditelnost detailů prodejce */}
                            <button className="toggle-btn" onClick={toggleDetails}>
                                {invoice.seller.name}
                            </button>
                        </div>
                        {/* Detailní informace o prodejci, zobrazené/skryté podle stavu showDetails */}
                        <div className={`seller-details-wrapper ${showDetails ? 'open' : ''}`}>
                            <SellerDetails seller={invoice.seller} />
                        </div>
                        {/* Nakupující */}
                        <p><strong>Nakupující:</strong>
                            <br />
                            {invoice.buyer.name}
                             {/* 
                            Komentovaná část s detaily nakupujícího, 
                            může se použít pokud budeš chtít zobrazit detailní info o nakupujícím stejně jako u prodejce
                        */}
                            {/*
                 <p>
                    <strong>DIČ:</strong>
                    <br />
                    {person.buyer.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {person.buyer.accountNumber}/{person.buyer.bankCode} 
                </p>
                <p>
                    <strong>IBAN:</strong><br/>
                    ({person.buyer.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {person.buyer.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {person.buyer.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {person.buyer.street}, {person.buyer.city} <br/>
                    {person.buyer.zip}, {country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {person.buyer.note}
                </p>
                
                 */}

                        </p>
                        <p><strong>Datum vystavení faktury:</strong>
                            <br />
                            {invoice.issued}
                        </p>

                        <p><strong>Datum splatnosti faktury:</strong>
                            <br />
                            {invoice.dueDate}
                        </p>
                        <p><strong>Částka bez DPH</strong>
                            <br />
                            {invoice.price}
                        </p>
                        <p><strong>DPH</strong>
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
