<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
   
    <style>
        body {
            background-color: #eefcf9;
            font-family: "Arial", sans-serif;
            margin: 0;
            padding: 0;
        }

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 70px;
            height: 100vh;
            background-color: #c3f6e1;
            transition: width 0.3s ease-in-out;
            z-index: 1;
        }

        .sidebar .logo {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 80px;
            margin-bottom: 20px;
        }

        .sidebar .logo img {
            max-width: 70px;
            max-height: 70px;
        }

        .sidebar .menu {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 40px;
        }

        .sidebar .menu a {
            display: block;
            margin-bottom: 90px;
            width: 40px;
            height: 40px;
            background-color: #c3f6e1;
            border-radius: 50%;
            text-align: center;
            line-height: 40px;
            color: #0d4c3a;
            transition: background-color 0.3s ease-in-out;
            font-size: 30px; /* Tamaño del icono */
            margin-right: 10px; /* Separación horizontal entre los iconos */
        }

        .sidebar .menu a:hover {
            background-color: #9ce0b4;
        }

        .content {
            margin-left: 70px;
            padding: 20px;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
        }

        .col-md-4 {
            flex: 0 0 33.33%;
            max-width: 33.33%;
            padding: 10px;
        }

        .kpi-section,
        .chart-section,
        .data-section {
            background-color: #f7fdfb;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            height: 100%;
        }

        .kpi-section h2,
        .chart-section h2,
        .data-section h2 {
            font-size: 24px;
            font-weight: bold;
            color: #0d4c3a;
        }

        .chart-container {
            position: relative;
            height: 300px;
        }
        
        .kpi-box {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
            width: 100%;
            text-align: center;
        }

        .kpi-title {
            font-size: 18px;
            align-items: center;
            font-weight: bold;
            color: #0d4c3a;
            margin: 50px;
        }

        .kpi-value {
            font-size: 24px;
            align-items: center;
            font-weight: bold;
            color: #3cb371; 
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th,
        td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ccc;
        }

        th {
            font-weight: bold;
            color: #0d4c3a;
        }

        td {
            font-size: 14px;
        }

        .font-bank {
            font-family: "Arial", sans-serif;
        }

        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #ffffff;
            border: 1px solid #ccc;
            padding: 20px;
            z-index: 2;
        }

        .popup label {
            display: block;
            margin-bottom: 10px;
        }

        .popup input {
            width: 100%;
            padding: 5px;
            margin-bottom: 20px;
        }

        .popup button {
            background-color: #c3f6e1;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #0d4c3a;
        }
        
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="logo">
            <img src="logo.png" alt="Logo">
        </div>
        <div class="menu">
            <a href="/" title="Inicio">&#8962;</a>
            <a href="#" title="Contacto">&#9743;</a>
            <a href="#" title="Ayuda">&#63;</a>
        </div>
    </div>

    <div class="content">
        <div class="row">
            <div class="col-md-4">
                <div class="kpi-section">   
                    <h2>Tu mejor cuota mensual es con</h2>
                    <div class="kpi-box">
                        <span class="kpi-title">{{ $banco }}</span>
                        <span class="kpi-value">{{ $cuotaMensualFormateada }}</span>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="chart-section">
                        <h2>Proporción de pago al capital en hipoteca por oferta Bancaria</h2>
                        <div class="chart-container">
                            <canvas id="pie_chart"></canvas>
                        </div>
                    </div>
                </div>
            <div class="col-md-4">
                <div class="chart-section">
                    <h2>Gráfica de cuotas mensuales</h2>
                    <div class="chart-container">
                        <canvas id="barChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="chart-section">
                    <h2>% TIN Mercado</h2>
                    <div class="chart-container">
                        <canvas id="lineChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="data-section">
                    <h2>Raw Data</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Banco</th>
                                <th>TIN</th>
                                <th>TAE</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td id="banco1">Banco 1</td>
                                <td id="tin1"></td>
                                <td id="tae1"></td>
                            </tr>
                            <tr>
                                <td id="banco2">Banco 2</td>
                                <td id="tin2"></td>
                                <td id="tae2"></td>
                            </tr>
                            <tr>
                                <td id="banco3">Banco 3</td>
                                <td id="tin3"></td>
                                <td id="tae3"></td>
                            </tr>
                        </tbody>
                    </table>
                    <a onclick="openPopup()" class="modify-button">Modificar</a>
                </div>
            </div>
        </div>
    </div>

    <div class="popup" id="popupForm">
        <form action="{{ route('formulario.actualizarFormulario') }}" method="POST">
            @csrf
            <label for="dinero_necesita">Dinero que necesita:</label>
            <input type="text" name="dinero_necesita" id="dinero_necesita">
            <label for="años_pago">Años de pago:</label>
            <input type="text" name="años_pago" id="años_pago">
            <button type="submit">Guardar</button>
        </form>
    </div>

    <script>
        function openPopup() {
            document.getElementById("popupForm").style.display = "block";
        }

        function closePopup() {
            document.getElementById("popupForm").style.display = "none";
        }
    </script>

    <script>
        const menuToggle = document.querySelector('.menu-toggle');
        const sidebar = document.querySelector('.sidebar');
        const content = document.querySelector('.content');

        menuToggle.addEventListener('click', () => {
            sidebar.classList.toggle('open');
            content.classList.toggle('open');
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var ctx = document.getElementById('barChart').getContext('2d');

            // Obtén los valores de las variables
            var cuotaMensual = {{ $cuotaMensual }};
            var cuotaMensual2 = {{ $cuotaMensual2 }};
            var cuotaMensual3 = {{ $cuotaMensual3 }};

            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Mejor oferta', '2º Mejor Oferta', '3º Mejor Oferta'],
                    datasets: [{
                        label: 'Bancos',
                        data: [cuotaMensual, cuotaMensual2, cuotaMensual3],
                        backgroundColor: 'rgba(75, 192, 192, 0.6)'
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            var lineCtx = document.getElementById('lineChart').getContext('2d');

            // Obtén los valores de las variables para la gráfica de líneas

            // Obtén los valores de las variables
            var mintinP = {{ $mintinP }};
            var mintinP2 = {{ $mintinP2 }};
            var mintinP3 = {{ $mintinP3 }};


            var cuotasMes = [mintinP, mintinP2, mintinP3];
            var meses = ['Banco1','Banco2','Banco3'];

            var lineChart = new Chart(lineCtx, {
                type: 'line',
                data: {
                    labels: meses,
                    datasets: [{
                        label: '% Current Month',
                        data: cuotasMes,
                        backgroundColor: 'rgba(75, 192, 192, 0.6)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
            
            // Table

              // Obtén los valores de las variables
                var banco1 = "{{ $banco }}";
                var tin1 = "{{ $mintinP }}";
                var tae1 = "{{ $taeP }}";
                var banco2 = "{{ $banco2 }}";
                var tin2 = "{{ $mintinP2 }}";
                var tae2 = "{{ $taeP2 }}";
                var banco3 = "{{ $banco3 }}";
                var tin3 = "{{ $mintinP3 }}";
                var tae3 = "{{ $taeP2 }}";

                // Asigna los valores a los elementos de la tabla
                document.getElementById("banco1").textContent = banco1;
                document.getElementById("tin1").textContent = tin1;
                document.getElementById("tae1").textContent = tae1;
                document.getElementById("banco2").textContent = banco2;
                document.getElementById("tin2").textContent = tin2;
                document.getElementById("tae2").textContent = tae2;
                document.getElementById("banco3").textContent = banco3;
                document.getElementById("tin3").textContent = tin3;
                document.getElementById("tae3").textContent = tae3;

                // Pie chart
                var pieCtx = document.getElementById('pie_chart').getContext('2d');

                var capitalPercentage1 = {{ $capitalPercentage1 }};
                var capitalPercentage2 = {{ $capitalPercentage2 }};
                var capitalPercentage3 = {{ $capitalPercentage3 }};

                var pieChart = new Chart(pieCtx, {
                    type: 'pie',
                    data: {
                        labels: ['Pago al capital'],
                        datasets: [{
                            label: 'Proporción de pago al capital en hipoteca por oferta Bancaria',
                            data: [capitalPercentage1, capitalPercentage2, capitalPercentage3],
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(192, 75, 75, 0.6)',
                                'rgba(255, 255, 0, 0.6)'
                            ],
                            borderColor: [
                                'rgba(75, 192, 192, 1)',
                                'rgba(192, 75, 75, 1)',
                                'rgba(255, 255, 0, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true
                    }
                });


        });
    </script>

</body>
</html>
