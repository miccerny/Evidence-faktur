import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import { apiGet } from "../../utils/api";

const PurchasesInvoice = () => {
    const { identificationNumber, buyer, seller } = useParams();
    const [invoices, setInvoices] = useState([]);

    useEffect(() => {
        if (!identificationNumber) return;

        apiGet(`/api/identification/${identificationNumber}/sales`)
            .then(data => {
                console.log("API data:", data);
                setInvoices(data || []);
            })
            .catch(error => {
                console.error("API error:", error);
            });

    }, [identificationNumber]);

    const formatDate = (dateArray) => {
        const [year, month, day] = dateArray;
        return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };
    return (

        <div>
            <h1>Výpis přijatých faktur pro IČ: {identificationNumber}</h1>
            <hr />
            {invoices.length === 0 ? (
                <p>Žádné přijaté faktury</p>
            ) : (
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
export default PurchasesInvoice;
