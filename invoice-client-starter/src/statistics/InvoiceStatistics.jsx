import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";

const InvoiceStatistics = () => {
    const[invoiceStats, setInvoiceStats] = useState([]);

    useEffect(() => {
        apiGet("/api/invoices/statistics")
        .then(data => {
            console.log("Statistika faktur:", data);
            setInvoiceStats(data);
        })
        .catch(error => console.error("chyba při načítání statistik:", error));
    }, []);

      if (!invoiceStats) {
        return <p>Načítání statistik…</p>;
    }

    return(
        <div>
        <div className="container">
            <div className="d-flex justify-content-center">
                <div key={invoiceStats.invoicesCount}>
                    <p className="card-title mb-0"><strong>Příjmy z faktur aktuální rok: </strong>{invoiceStats.currentYearSum} Kč</p>
                    <p className="text mb-0"><strong>Celkové příjmy z faktur: </strong>{invoiceStats.allTimeSum} Kč</p>
                </div>
            </div>
        </div>
        </div>
    )
}
export default InvoiceStatistics;