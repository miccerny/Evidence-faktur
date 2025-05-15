import React from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import '../styles/CardStyles.css'
import '../styles/ButtonStyles.css'

const ITEMS_PER_PAGE = 5;

const InvoiceTable = ({ label, items, deleteInvoice, page, setPage, totalPages, totalElements, toggleCard, openCardId }) => {

    if (!Array.isArray(items)) {
        return <div>Načítám faktury…</div>;
    }
    return (
        <div className="container">
            <p>
                Počet faktur: {totalElements}
            </p>
            <h3>
                Číslo faktury
            </h3>
            <div className="row g-3">
                {items.map((invoice) => (
                    <div className="col-md-6 col-lg-12" key={invoice._id + 1}>
                        <div className="card shadow-sm h-100">
                            <div className="card-body position-relative">
                                <div className="d-flex justify-content-between align-items-center m-2"
                                    onClick={() => toggleCard(invoice._id)}>
                                    <h5 className="custom-card-title">
                                        {invoice.invoiceNumber}
                                    </h5>

                                    <button
                                        className="btn btn-sm btn-outline-primary"
                                    >
                                        {openCardId === invoice._id ? "X" : "Akce"}
                                    </button>
                                </div>
                                <div className={`action-panel mt-3 ${openCardId === invoice._id ? "open" : ""}`}>
                                    <div className="d-grid gap-2">
                                        <Link
                                            to={"/invoices/show/" + invoice._id}
                                            className="btn btn-info"
                                        >
                                            Zobrazit
                                        </Link>
                                        <Link
                                            to={"/invoices/edit/" + invoice._id}
                                            className="btn btn-outline-warning text-black">
                                            Upravit
                                        </Link>
                                        <button
                                            onClick={() => deleteInvoice(invoice._id)}
                                            className="btn btn-sm btn-danger"
                                        >
                                            Odstranit
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="d-flex justify-content-center align-items-center mt-4">
                <div className="d-flex align-items-center">
                    <button
                        className="btn btn-outline-secondary me-2"
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        Předchozí
                    </button>
                    <button
                        className="btn btn-outline-secondary"
                        disabled={page + 1 >= totalPages}
                        onClick={() => setPage(page + 1)}
                    >
                        Další
                    </button>
                    <span className="ms-3">Stránka {page + 1} z {totalPages}</span>
                </div>
            </div>
            <div className="mt-3">
                <Link to={"/invoices/create"} className="btn btn-success">
                    Nová faktura
                </Link>
            </div>
        </div>
    )
}
export default InvoiceTable;