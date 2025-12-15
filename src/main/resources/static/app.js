const API_URL = 'http://localhost:8080/api';
let currentUser = null;

window.onload = function () {
    carregarSelects();
    listarTodosGames();
};

function switchTab(tabName) {
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));

    // Corrigido: buscar o botão que foi clicado pelo data attribute ou texto
    const clickedBtn = Array.from(document.querySelectorAll('.tab-btn')).find(btn => 
        btn.getAttribute('onclick').includes(tabName)
    );
    if (clickedBtn) clickedBtn.classList.add('active');
    
    document.getElementById(`tab-${tabName}`).classList.add('active');

    if (tabName === 'games') listarTodosGames();
    if (tabName === 'usuarios') listarUsuarios();
}

function mostrarMensagem(elementId, mensagem, tipo) {
    const el = document.getElementById(elementId);
    el.textContent = mensagem;
    el.className = `message ${tipo}`;
    el.style.display = 'block';
    setTimeout(() => el.style.display = 'none', 4000);
}

async function carregarSelects() {
    try {
        const [gamesResp, usuariosResp] = await Promise.all([
            fetch(`${API_URL}/games`).then(r => r.json()),
            fetch(`${API_URL}/usuarios`).then(r => r.json())
        ]);

        const games = Array.isArray(gamesResp) ? gamesResp : (gamesResp.content || []);
        const usuarios = Array.isArray(usuariosResp) ? usuariosResp : (usuariosResp.content || []);

        const gameSelects = ['reviewGame', 'selectGameReviews', 'jogadoGame'];
        gameSelects.forEach(selectId => {
            const select = document.getElementById(selectId);
            if (select) {
                select.innerHTML = '<option value="">Selecione...</option>';
                games.forEach(g => {
                    select.innerHTML += `<option value="${g.id}">${g.nome}</option>`;
                });
            }
        });

        const userSelects = ['reviewUsuario', 'jogadoUsuario', 'selectUsuarioHistorico'];
        userSelects.forEach(selectId => {
            const select = document.getElementById(selectId);
            if (select) {
                select.innerHTML = '<option value="">Selecione...</option>';
                usuarios.forEach(u => {
                    select.innerHTML += `<option value="${u.id}">${u.nome}</option>`;
                });
            }
        });
    } catch (error) {
        console.error('Erro ao carregar selects:', error);
    }
}

async function listarTodosGames() {
    try {
        const response = await fetch(`${API_URL}/games`);
        const data = await response.json();
        const games = Array.isArray(data) ? data : (data.content || []);
        exibirGames(games);
    } catch (error) {
        console.error('Erro:', error);
        const container = document.getElementById('resultadosGames');
        if (container) {
            container.innerHTML = '<p style="color: #ff6b6b;">❌ Erro: Backend não está rodando! Inicie o servidor Spring Boot.</p>';
        }
    }
}

async function pesquisarGames() {
    const termo = document.getElementById('searchGames').value.trim();
    if (!termo) {
        listarTodosGames();
        return;
    }
    try {
        const response = await fetch(`${API_URL}/games/pesquisar?nome=${encodeURIComponent(termo)}`);
        const data = await response.json();
        const games = Array.isArray(data) ? data : (data.content || []);
        exibirGames(games);
    } catch (error) {
        console.error('Erro:', error);
    }
}

function exibirGames(games) {
    const container = document.getElementById('resultadosGames');
    if (!container) return;
    
    if (!Array.isArray(games) || games.length === 0) {
        container.innerHTML = '<p>Nenhum game encontrado.</p>';
        return;
    }
    container.innerHTML = games.map(g => `
        <div class="game-card">
            <h3>${g.nome}</h3>
            <p><strong>Developer:</strong> ${g.developer || 'N/A'}</p>
            <p><strong>Publisher:</strong> ${g.publisher || 'N/A'}</p>
            <p><strong>Lançamento:</strong> ${g.dataLancamento || 'N/A'}</p>
            <p>${g.descricao || 'Sem descrição'}</p>
            ${g.genero ? `<span class="genre">${g.genero}</span>` : ''}
        </div>
    `).join('');
}

async function cadastrarGame(e) {
    e.preventDefault();
    const game = {
        nome: document.getElementById('gameNome').value,
        dataLancamento: document.getElementById('gameData').value,
        developer: document.getElementById('gameDeveloper').value,
        publisher: document.getElementById('gamePublisher').value,
        descricao: document.getElementById('gameDescricao').value,
        genero: document.getElementById('gameGenero').value
    };

    try {
        const response = await fetch(`${API_URL}/games`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(game)
        });

        if (response.ok) {
            mostrarMensagem('msgCadastroGame', 'Game cadastrado com sucesso!', 'success');
            document.getElementById('formCadastroGame').reset();
            carregarSelects();
            listarTodosGames();
        } else {
            mostrarMensagem('msgCadastroGame', 'Erro ao cadastrar game.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        mostrarMensagem('msgCadastroGame', 'Erro ao conectar com servidor.', 'error');
    }
}

async function salvarReview() {
    const review = {
        usuarioId: parseInt(document.getElementById('reviewUsuario').value),
        gameId: parseInt(document.getElementById('reviewGame').value),
        nota: parseInt(document.getElementById('reviewNota').value),
        avaliacao: document.getElementById('reviewAvaliacao').value
    };

    if (!review.usuarioId || !review.gameId) {
        mostrarMensagem('msgReview', 'Selecione usuário e game!', 'error');
        return;
    }

    if (review.nota < 0 || review.nota > 10) {
        mostrarMensagem('msgReview', 'Nota deve estar entre 0 e 10!', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/reviews`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(review)
        });

        if (response.ok) {
            mostrarMensagem('msgReview', 'Review salva com sucesso!', 'success');
            document.getElementById('reviewNota').value = '';
            document.getElementById('reviewAvaliacao').value = '';
        } else {
            mostrarMensagem('msgReview', 'Erro ao salvar review.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        mostrarMensagem('msgReview', 'Erro ao conectar com servidor.', 'error');
    }
}

async function carregarReviewsGame() {
    const gameId = document.getElementById('selectGameReviews').value;
    const container = document.getElementById('listaReviews');
    if (!container) return;

    if (!gameId) {
        container.innerHTML = '';
        return;
    }

    try {
        const [reviews, mediaResp] = await Promise.all([
            fetch(`${API_URL}/reviews/game/${gameId}`).then(r => r.json()),
            fetch(`${API_URL}/reviews/game/${gameId}/media`).then(r => r.json())
        ]);

        if (reviews.length === 0) {
            container.innerHTML = '<p>Nenhuma review encontrada.</p>';
            return;
        }

        const media = mediaResp.media || 0;
        container.innerHTML = `
            <div style="background: rgba(102, 126, 234, 0.3); padding: 15px; border-radius: 10px; margin: 20px 0;">
                <h3>Média: ${media.toFixed(1)} ⭐</h3>
                <p>Total de ${reviews.length} review(s)</p>
            </div>
            ${reviews.map(r => `
                <div class="review-item">
                    <div class="review-header">
                        <strong>${r.usuarioNome || 'Usuário'}</strong>
                        <span class="review-nota">${r.nota}/10</span>
                    </div>
                    <p>${r.avaliacao}</p>
                    <small style="opacity: 0.7;">${r.dataReview || ''}</small>
                </div>
            `).join('')}
        `;
    } catch (error) {
        console.error('Erro:', error);
        container.innerHTML = '<p>Erro ao carregar reviews.</p>';
    }
}

async function cadastrarUsuario(e) {
    e.preventDefault();
    const usuario = {
        nome: document.getElementById('usuarioNome').value,
        email: document.getElementById('usuarioEmail').value,
        senha: document.getElementById('usuarioSenha').value
    };

    try {
        const response = await fetch(`${API_URL}/usuarios`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(usuario)
        });

        if (response.ok) {
            mostrarMensagem('msgUsuario', 'Usuário cadastrado com sucesso!', 'success');
            document.getElementById('formUsuario').reset();
            carregarSelects();
            listarUsuarios();
        } else {
            mostrarMensagem('msgUsuario', 'Erro: email já existe!', 'error');
        }
    } catch (error) {
        console.error('Erro ao cadastrar usuário:', error);
        mostrarMensagem('msgUsuario', 'Erro ao conectar com servidor.', 'error');
    }
}

async function listarUsuarios() {
    try {
        const response = await fetch(`${API_URL}/usuarios`);
        const usuarios = await response.json();
        const container = document.getElementById('listaUsuarios');
        if (!container) return;

        if (usuarios.length === 0) {
            container.innerHTML = '<p>Nenhum usuário cadastrado.</p>';
            return;
        }

        container.innerHTML = usuarios.map(u => `
            <div class="usuario-card">
                <div class="usuario-info">
                    <h3>${u.nome}</h3>
                    <p>${u.email}</p>
                </div>
                <div class="usuario-stats">
                    <div class="stat">
                        <div class="stat-number">${u.qtdJogosJogados || 0}</div>
                        <div class="stat-label">Jogos</div>
                    </div>
                    <div class="stat">
                        <div class="stat-number">${u.qtdReviewsFeitas || 0}</div>
                        <div class="stat-label">Reviews</div>
                    </div>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Erro ao listar usuários:', error);
    }
}

async function registrarJogoJogado() {
    const jogado = {
        usuarioId: parseInt(document.getElementById('jogadoUsuario').value),
        gameId: parseInt(document.getElementById('jogadoGame').value),
        dataJogada: document.getElementById('jogadoData').value || null
    };

    if (!jogado.usuarioId || !jogado.gameId) {
        mostrarMensagem('msgJogado', 'Selecione usuário e game!', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/jogos-jogados`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(jogado)
        });

        if (response.ok) {
            mostrarMensagem('msgJogado', 'Jogo registrado com sucesso!', 'success');
            document.getElementById('jogadoData').value = '';
        } else {
            mostrarMensagem('msgJogado', 'Erro: jogo já registrado para este usuário!', 'error');
        }
    } catch (error) {
        console.error('Erro ao registrar jogo:', error);
        mostrarMensagem('msgJogado', 'Erro ao conectar com servidor.', 'error');
    }
}

async function carregarHistoricoUsuario() {
    const usuarioId = document.getElementById('selectUsuarioHistorico').value;
    const container = document.getElementById('historicoJogos');
    if (!container) return;

    if (!usuarioId) {
        container.innerHTML = '';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/jogos-jogados/usuario/${usuarioId}`);
        const jogos = await response.json();

        if (jogos.length === 0) {
            container.innerHTML = '<p>Nenhum jogo registrado para este usuário.</p>';
            return;
        }

        container.innerHTML = `
            <div style="margin-top: 20px;">
                <h3>Total: ${jogos.length} game(s) jogado(s)</h3>
                ${jogos.map(j => `
                    <div class="review-item">
                        <strong>${j.gameNome || 'Game'}</strong>
                        <p>Jogado em: ${j.dataJogada || 'Data não informada'}</p>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Erro ao carregar histórico:', error);
        container.innerHTML = '<p>Erro ao carregar histórico.</p>';
    }
}