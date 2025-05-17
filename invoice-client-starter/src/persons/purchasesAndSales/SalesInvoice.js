import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { apiGet } from "../../utils/api";

console.log("Component PurchasesInvoice mounted");

const SalesInvoice = () => {
    // Získání IČ z URL (např. /sales/12345678)
    const { identificationNumber } = useParams();
    // Stav pro uložení načtených faktur
    const [invoices, setInvoices] = useState([]);
    // Pro kontrolu v konzoli
    console.log("Identification number from URL:", identificationNumber);

    // useEffect zavolá API při změně IČ
    useEffect(() => {
        if (!identificationNumber) return;

        // Volání API pro získání vystavených faktur daného subjektu
        apiGet(`/api/identification/${identificationNumber}/sales`)
            .then(data => {
                console.log("API error:", data);
                console.log("Identification number from URL:", identificationNumber);
                setInvoices(data || []);
            })
            .catch(error => {
                console.error("API error:", error);
            });

    }, [identificationNumber]);

    // Pomocná funkce pro formátování pole [YYYY, MM, DD] na řetězec YYYY-MM-DD
    const formatDate = (dateArray) => {
        const [year, month, day] = dateArray;
        return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };

    return (
        <div>
            {/* Nadpis s IČ */}
            <h1>Výpis vystavených faktur pro IČ: {identificationNumber}</h1>
            <hr />
            {/* Pokud nejsou žádné faktury, zobraz zprávu */}
            {invoices && invoices.length === 0 ? (
                <p>Žádné vystavené faktury</p>
            ) : (
                // Tabulka s vystavenými fakturami
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
                        {invoices.map(invoice => (
                            <tr key={invoice._id}>
                                <td>{invoice.invoiceNumber}</td>
                                <td>{invoice.seller.name}</td>
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
export default SalesInvoice;
