/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */


import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import '../styles/CardStyles.css'
import '../styles/ButtonStyles.css'

const ITEMS_PER_PAGE = 5;

const PersonTable = ({ items, deletePerson, page, setPage, totalPages, totalElements, openCardId, toggleCard}) => {

    console.log("Aktuální stránka (page):", page, typeof page);

    if (!Array.isArray(items)) {
        return <div>Načítání dat…</div>;
    }

    

    return (
        <>
        
        <div className="container">
            <p>
                Počet osob: {totalElements}
            </p>
            <div className="row g-3">
                {items.map((person, index) => (
                    <div className="col-md-6 col-lg-12" key={person._id}>
                        <div className="custom-card shadow-sm h-100">
                            <div className="card-body position-relative">
                                <div className="d-flex justify-content-between align-items-center m-1"
                                    onClick={() => toggleCard(person._id)}
                                >
                                    <h5 className="custom-card-title">
                                        {(page * ITEMS_PER_PAGE) + index + 1} - {person.name}
                                    </h5>
                                    <button className="btn btn-sm btn-outline-primary">
                                        {openCardId === person._id ? "X" : "Akce"}
                                    </button>
                                </div>
                                <div className={`action-panel mt-3 ${openCardId === person._id ? "open" : ""}`}>
                                    <div className="d-grid gap-2">
                                        <Link
                                            to={"/persons/show/" + person._id}
                                            className="button button-detail button-detail:hover"
                                        >
                                            Zobrazit
                                        </Link>
                                        <Link
                                            to={"/persons/edit/" + person._id}
                                            className="button button-edit button-edit:hover"
                                        >
                                            Upravit
                                        </Link>
                                        <button
                                            onClick={() =>
                                                deletePerson(person._id)
                                            }
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
            <div className="d-flex justify-content-center align-items-center mt-4">
                <div>
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
                <Link to={"/persons/create"} className="btn btn-success">
                    Nová osoba
                </Link>
            </div>
        </div>
        </>
    );
};
export default PersonTable;
