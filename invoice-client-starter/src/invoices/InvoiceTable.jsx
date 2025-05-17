import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import '../styles/CardStyles.css'
import '../styles/ButtonStyles.css'

const InvoiceTable = ({
    items,              // Pole faktur, které se mají zobrazit
    deleteInvoice,      // Funkce pro smazání faktury
    page,               // Aktuální stránka (indexovaná od 0)
    setPage,            // Funkce pro nastavení stránky
    totalPages,         // Celkový počet dostupných stránek
    totalElements,      // Celkový počet faktur
    toggleCard,         // Funkce pro přepínání rozbalení detailu faktury
    openCardId          // ID faktury, jejíž detail je rozbalený
     }) => {

        // Pokud `items` není pole (např. data se ještě nenačetla), zobrazíme načítací zprávu
    if (!Array.isArray(items)) {
        return <div>Načítám faktury…</div>;
    }
    return (
        <div className="container">
             {/* Zobrazení celkového počtu faktur */}
            <p>
                Počet faktur: {totalElements}
            </p>
            {/* Nadpis tabulky */}
            <h3>
                Číslo faktury
            </h3>
            {/* Grid řádek pro faktury */}
            <div className="row g-3">
                {items.map((invoice) => (
                    <div className="col-md-6 col-lg-12" key={invoice._id + 1}>
                        <div className="card shadow-sm h-100">
                            <div className="card-body position-relative">
                                {/* Hlavička karty s číslem faktury a tlačítkem pro rozbalení akcí */}
                                <div className="d-flex justify-content-between align-items-center m-2"
                                    onClick={() => toggleCard(invoice._id)}> {/*Přepnutí zobrazení akčních tlačítek*/} 
                                    <h5 className="custom-card-title">
                                        {invoice.invoiceNumber}
                                    </h5>
                                    <button className="btn btn-sm btn-outline-primary">
                                        {/* Pokud je karta otevřená, zobrazí "X" pro zavření, jinak "Akce" */}
                                        {openCardId === invoice._id ? "X" : "Akce"}
                                    </button>
                                </div>
                                {/* Panel s akcemi, zobrazuje se jen pokud je karta otevřená */}
                                <div className={`action-panel mt-3 ${openCardId === invoice._id ? "open" : ""}`}>
                                    <div className="d-grid gap-2">
                                        {/* Odkaz na detail faktury */}
                                        <Link
                                            to={"/invoices/show/" + invoice._id}
                                            className="button button-detail button-detail:hover"
                                        >
                                            Zobrazit
                                        </Link>
                                         {/* Odkaz na úpravu faktury */}
                                        <Link
                                            to={"/invoices/edit/" + invoice._id}
                                            className="button button-edit button-edit:hover">
                                            Upravit
                                        </Link>
                                        {/* Tlačítko pro odstranění faktury */}
                                        <button
                                            onClick={() => deleteInvoice(invoice._id)}
                                            className="button button-delete button-delete:hover"
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
            {/* Ovládání stránkování */}
            <div className="d-flex justify-content-center align-items-center mt-4">
                <div className="d-flex align-items-center">
                    {/* Tlačítko na předchozí stránku, deaktivované na první stránce */}
                    <button
                        className="btn btn-outline-secondary me-2"
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        Předchozí
                    </button>
                    {/* Tlačítko na další stránku, deaktivované na poslední stránce */}
                    <button
                        className="btn btn-outline-secondary"
                        disabled={page + 1 >= totalPages}
                        onClick={() => setPage(page + 1)}
                    >
                        Další
                    </button>
                     {/* Informace o aktuální stránce */}
                    <span className="ms-3">Stránka {page + 1} z {totalPages}</span>
                </div>
            </div>
            {/* Tlačítko pro vytvoření nové faktury */}
            <div className="mt-3">
                <Link to={"/invoices/create"} className="btn btn-success">
                    Nová faktura
                </Link>
            </div>
        </div>
    )
}
export default InvoiceTable;