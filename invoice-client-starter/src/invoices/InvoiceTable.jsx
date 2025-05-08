import React from "react";
import { Link } from "react-router-dom";

const ITEMS_PER_PAGE = 5;

const InvoiceTable = ({ label, items, deleteInvoice, page, setPage, totalPages, totalElements }) => {

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
                            <div className="d-flex justify-content-between align-items-center m-2">
                                <h5 className="card-title">
                                {invoice.invoiceNumber}
                                </h5>
                                <div className="dropdown">
                                    <button
                                        className="btn btn-primary btn-sm dropdown-toggle"
                                        type="button"
                                        id={`dropdownMenuButtton-${invoice._id}`}
                                        data-bs-toggle="dropdown"
                                        aria-expanded="false"
                                    >
                                        Akce
                                    </button>
                                    <ul className="dropdown-menu" aria-labelledby={`dropdownMenuButton-${invoice._id}`}>
                                        <li>
                                            <Link
                                                to={"/invoices/show/" + invoice._id}
                                                className="dropdown-item btn btn-info"
                                            >
                                                Zobrazit
                                            </Link>
                                        </li>
                                        <li>
                                            <Link
                                                to={"/invoices/edit/" + invoice._id}
                                                className="dropdown-item btn btn-sm btn-warning"
                                            >
                                                Upravit
                                            </Link>
                                        </li>
                                        <li>
                                            <button
                                                onClick={() => deleteInvoice(invoice._id)}
                                                className="dropdown-item btn btn-sm btn-danger"
                                            >
                                                Odstranit
                                            </button>
                                        </li>
                                    </ul>
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