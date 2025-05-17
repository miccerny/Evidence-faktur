import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { apiGet } from "../../utils/api";

const PurchasesInvoice = () => {
    // Získání IČ (identificationNumber) z URL parametrů
    const {identificationNumber} = useParams();
    // Stav pro uchování přijatých faktur
    const [invoices, setInvoices] = useState([]);

    // useEffect pro načtení faktur, když se změní identificationNumber
    useEffect(() => {
        if (!identificationNumber) return;

        // API volání na přijaté faktury pro dané IČ
        apiGet(`/api/identification/${identificationNumber}/purchases`)
            .then(data => {
                console.log("API data:", data);
                setInvoices(data || []);
            })
            .catch(error => {
                console.error("API error:", error);
            });
    }, [identificationNumber]);
// Pomocná funkce pro formátování pole s datem [yyyy, mm, dd] na řetězec ve formátu YYYY-MM-DD
    const formatDate = (dateArray) => {
        const [year, month, day] = dateArray;
        return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };

    return (
        <div>
            {/* Nadpis s informací o IČ */}
            <h1>Výpis přijatých faktur pro IČ: {identificationNumber}</h1>
            <hr />
            {/* Zobrazení zprávy, pokud nejsou žádné faktury */}
            {invoices.length === 0 ? (
                <p>Žádné přijaté faktury</p>
            ) : (
                // Tabulka s fakturami
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Číslo faktury</th>
                            <th>Dodavatel</th>
                            <th>Produkt</th>
                            <th>Vystaveno</th>
                            <th>Datum splatnosti</th>
                            <th>Cena</th>
                            <th>DPH</th>
                        </tr>
                    </thead>
                    <tbody>
                        {/* Iterace přes faktury a jejich zobrazení */}
                        {invoices.map(invoice => (
                            <tr key={invoice._id}>
                                <td>{invoice.invoiceNumber}</td>
                                <td>{invoice.buyer.name}</td>
                                <td>{invoice.product}</td>
                                <td>{formatDate(invoice.issued)}</td>  {/* Formátování data vystavení */}
                                <td>{formatDate(invoice.dueDate)}</td>  {/* Formátování data splatnosti */}
                                <td>{invoice.price} Kč</td>
                                <td>{invoice.vat} %</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    )
}
export default PurchasesInvoice;
