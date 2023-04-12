const PIZZAS_URL = 'http://localhost:8080/api/v1/pizzas';
const contentElement = document.getElementById('content');
const toggleForm = document.getElementById('toggle-form');
const pizzaForm = document.getElementById('pizza-form');


const getpizzaList = async () => {
    const response = await fetch(PIZZAS_URL);
    return response;
};

const postpizza = async (jsonData) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData,
    };
    const response = await fetch(PIZZAS_URL, fetchOptions);
    return response;
};

const deletepizzaById = async (pizzaId) => {
    const response = await fetch(PIZZAS_URL + '/' + pizzaId, { method: 'DELETE' });
    return response;
};
const toggleFormVisibility = () => {
    document.getElementById('form').classList.toggle('d-none');
};


const createDeleteBtn = (pizza) => {
    let btn = '';
    const offers = pizza.offers;
    if (offers !== null && offers > 0) {
        // disabled button
        btn = `<button class="btn btn-danger" disabled>
            Delete
        </button>`;
    } else {
        btn = `<button data-id="${pizza.id}" class="btn btn-danger">
            Delete
        </button>`;
    }
    return btn;
};

const createpizzaItem = (item) => {
    return `<div class="col-4">
              <div class="card h-100">
                  <div class="card-header">Name: ${item.nome}</div>
                  <div class="card-body">
                    <h5 class="card-title">${item.descrizione}</h5>
                      
                    <p class="card-text d-flex justify-content-between">
                          <span>${item.prezzo}</span>
                    </p>
                    
                    <div>${createDeleteBtn(item)}</div>

                    <div class="card-footer">${createIngredientsList(item.ingredients)}</div>
                  </div>
              </div>
      </div>`;
};

const createpizzaList = (data) => {
    console.log(data);
    let list = `<div class="row gy-3">`;
    // add pizza items
    data.forEach((element) => {
        list += createpizzaItem(element);
    });
    list += '</div>';
    return list;
};

const createIngredientsList = (ingredients) => {
    let ingredientsHtml = '<p>';
    ingredients.forEach((ingr) => {
        ingredientsHtml += `<span class="mx-1 badge text-bg-info">${ingr.name}</span>`;
    });
    ingredientsHtml += '</p>';
    return ingredientsHtml;
};

const loadData = async () => {
    const response = await getpizzaList();
    console.log(response);
    if (response.ok) {
        const data = await response.json();
        contentElement.innerHTML = createpizzaList(data);
        const deleteBtns = document.querySelectorAll('button[data-id]');
        console.log(deleteBtns);
        for (let btn of deleteBtns) {
            btn.addEventListener('click', () => {
                deletepizza(btn.dataset.id);
            });
        }
    } else {
        console.log('ERROR');
    }
};
const savepizza = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const formDataObj = Object.fromEntries(formData.entries());

    const formDataJson = JSON.stringify(formDataObj);

    const response = await postpizza(formDataJson);

    const responseBody = await response.json();
    if (response.ok) {

        loadData();

        event.target.reset();
    } else {
        console.log('ERROR');
        console.log(response.status);
        console.log(responseBody);
    }
};
const deletepizza = async (pizzaId) => {
    console.log('delete pizza ' + pizzaId);

    const response = await deletepizzaById(pizzaId);
    if (response.ok) {
        loadData();
    } else {
        console.log('ERROR');
        console.log(response.status);
    }
};

toggleForm.addEventListener('click', toggleFormVisibility);
pizzaForm.addEventListener('submit', savepizza);
loadData();